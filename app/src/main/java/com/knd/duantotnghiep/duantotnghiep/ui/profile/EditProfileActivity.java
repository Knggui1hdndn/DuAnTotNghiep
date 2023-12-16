package com.knd.duantotnghiep.duantotnghiep.ui.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationRequest;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityEditProfileBinding;
import com.knd.duantotnghiep.duantotnghiep.models.MessageResponse;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.respository.UserRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.CheckPermission;
import com.knd.duantotnghiep.duantotnghiep.utils.FileProviderUtils;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.knd.duantotnghiep.duantotnghiep.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;

@AndroidEntryPoint
public class EditProfileActivity extends BaseActivity<ActivityEditProfileBinding> {
    private Uri uriImg;
    private File file;
    @Inject
    public UserPreferencesManager userPreferencesManager;
    private User user;
    @Inject
    public UserRepository userRepository;
    private GoogleMap googleMap;
    @SuppressLint("MissingPermission")
    private final ActivityResultLauncher<String[]> activityResult = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), o -> {
        if (checkAllValuesTrue(o)) {
            googleMap.setMyLocationEnabled(true);
            getCurrentLocation();
        } else {
            showMessage("Permissions denied");
        }
    });

    public boolean checkAllValuesTrue(Map<String, Boolean> map) {
        for (Boolean value : map.values()) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    private FusedLocationProviderClient fusedLocationProviderClient;


    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        fusedLocationProviderClient.getCurrentLocation(new CurrentLocationRequest
                        .Builder()
                        .setPriority(LocationRequest.QUALITY_HIGH_ACCURACY)
                        .build(), null)
                .addOnSuccessListener(location -> getLocationByLaLng(new LatLng(location.getLatitude(), location.getLongitude())));
    }

    private void getLocationByLaLng(LatLng latLng) {
        try {
            List<Address> geocoder = new Geocoder(this).getFromLocation(latLng.latitude, latLng.longitude, 15);
            assert geocoder != null;
            if (!geocoder.isEmpty()) {
                binding.txtInputAddress.getEditText().setText(geocoder.get(0).getAddressLine(0) + "");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.clear();
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                markerOptions.title("Địa điểm giao hàng tới");
                googleMap.addMarker(markerOptions);
            }

        } catch (IOException ignored) {

        }
    }

    private final ActivityResultLauncher<PickVisualMediaRequest> pickImage = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
        if (uri != null) {
            binding.avatar.setImageURI(uri);
            file = FileProviderUtils.saveImage(binding.avatar, this);
        }
    });

    private final ActivityResultLauncher<Uri> openCamera = registerForActivityResult(new ActivityResultContracts.TakePicture(), b -> {
        if (b) {
            binding.avatar.setImageURI(uriImg);
            file = FileProviderUtils.saveImage(binding.avatar, this);
        }
    });
    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), b -> {
        if (b) {
            openCamera.launch(uriImg);
        } else {
            showMessage("Permission denied");
        }
    });

    @Override
    protected void initData() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        user = userPreferencesManager.getCurrentUser();
        uriImg = FileProviderUtils.getUri(getDataDir().getPath() + "/avatar.png", this);
        file = FileProviderUtils.saveImage(binding.avatar, this);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_edit_profile;
    }

    @Override
    protected void initView() {
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        setUpMap();
        binding.openCamera.setOnClickListener(view -> {
            if (CheckPermission.isPermissionCamera(this)) {
                openCamera.launch(uriImg);
            } else {
                requestPermission.launch(CheckPermission.ACCESS_CAMERA);
            }
        });

        binding.pickImage.setOnClickListener(view -> {
            pickImage.launch(new PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                    .build());
        });
        binding.getLocation.setOnClickListener(latLng -> {
            if (!CheckPermission.isPermissionLocation(this)) {
                activityResult.launch(new String[]{CheckPermission.ACCESS_COARSE_LOCATION, CheckPermission.ACCESS_FINE_LOCATION});
            } else {
                getCurrentLocation();
            }
        });
        setDataEditText();
    }

    @SuppressLint("SetTextI18n")
    private void setDataEditText() {
        setTextEditText(binding.txtInputName, user.getName());
        setTextEditText(binding.txtInputPhoneNumber, user.getPhoneNumber() + "");
        setTextEditText(binding.txtInputAddress, user.getAddress() + "");
        setTextEditText(binding.txtInputEmail, user.getEmail() + "");
        Utils.loadImage(binding.avatar, user.getAvatar());
    }

    private void setTextEditText(TextInputLayout txtInputName, String s) {
        txtInputName.getEditText().setText(s);
    }

    @SuppressLint("MissingPermission")
    private void setUpMap() {
        SupportMapFragment fragmentById = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert fragmentById != null;
        fragmentById.getMapAsync(mMap -> {
            if (CheckPermission.isPermissionLocation(this)) mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMaxZoomPreference(12);
            mMap.setMinZoomPreference(12);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setOnMapClickListener(this::getLocationByLaLng);
            this.googleMap = mMap;
        });
    }

    @Override
    protected void initObserver() {
        userRepository.editUser.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<User>() {
            @Override
            public void handleSuccess(User data) {
                userPreferencesManager.saveUser(data);
                finish();
            }

            @Override
            public void handleError(String error) {
                showMessage(error);
            }

            @Override
            public void handleLoading() {

            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.confirm) {
          try {
              if (binding.txtInputName.getEditText().getText().toString().isEmpty() ||
                      binding.txtInputAddress.getEditText().getText().toString().isEmpty()||
                      binding.txtInputPhoneNumber.getEditText().getText().toString().isEmpty()||
                      binding.txtInputEmail.getEditText().getText().toString().isEmpty()){
                  showMessage("Vui lòng không bỏ trống thông tin");
              }else {
                  updateProfile();
              }
          }catch (Exception e) {
              showMessage("Vui lòng không bỏ trống thông tin");
          }
          }
        return super.onOptionsItemSelected(item);
    }

    private void updateProfile() {
        MultipartBody.Part reqFile = createMultipartPart("avatar", file);
        RequestBody name = createRequestBody(binding.txtInputName);
        RequestBody email = createRequestBody(binding.txtInputEmail);
        RequestBody address = createRequestBody(binding.txtInputAddress);
        RequestBody phoneNumber = createRequestBody(binding.txtInputPhoneNumber);
        userRepository.editUser(reqFile, name, address, phoneNumber, email);
    }

    public MultipartBody.Part createMultipartPart(String name, File file) {
        return MultipartBody.Part.createFormData(name, file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
    }

    private RequestBody createRequestBody(TextInputLayout textInputLayout) {
        return RequestBody.create(textInputLayout.getEditText().getText().toString(), MediaType.parse("multipart/form-data"));
    }

    @Override
    public ActivityEditProfileBinding getViewBinding() {
        return ActivityEditProfileBinding.inflate(getLayoutInflater());
    }
}