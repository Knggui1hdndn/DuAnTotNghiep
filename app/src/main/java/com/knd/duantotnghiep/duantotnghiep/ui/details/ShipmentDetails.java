package com.knd.duantotnghiep.duantotnghiep.ui.details;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.CurrentLocationRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.textfield.TextInputLayout;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.databinding.ActivityShipmentDetailsBinding;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.utils.CheckPermission;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ShipmentDetails extends BaseActivity<ActivityShipmentDetailsBinding> {
    @Override
    public ActivityShipmentDetailsBinding getViewBinding() {
        return ActivityShipmentDetailsBinding.inflate(getLayoutInflater());
    }

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

    @Inject
    public UserPreferencesManager userPreferencesManager;
    private User user;


    @Override
    protected void initData() {
        user = userPreferencesManager.getCurrentUser();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    @Override
    protected void initView() {
        setUpToolBar(binding.toolbar, true, getDrawable(R.drawable.baseline_arrow_back_ios_24));
        setDataEditText();
        setUpMap();

        binding.complete.setOnClickListener(view -> {
            sendBroadcast();
        });

        binding.getLocation.setOnClickListener(latLng -> {
            if (!CheckPermission.isPermissionLocation(this)) {
                activityResult.launch(new String[]{CheckPermission.ACCESS_COARSE_LOCATION, CheckPermission.ACCESS_FINE_LOCATION});
            } else {
                getCurrentLocation();
            }
        });
    }

    private void sendBroadcast() {
        Intent intent = new Intent("edit");
        user.setAddress(getTextFromTextLayout(binding.txtInputAddress));
        user.setName(getTextFromTextLayout(binding.txtInputName));
        user.setPhoneNumber(getTextFromTextLayout(binding.txtInputPhoneNumber));
        intent.putExtra("user", user);
        sendBroadcast(intent);
        finish();
    }

    private String getTextFromTextLayout(TextInputLayout txtInputAddress) {
        return Objects.requireNonNull(txtInputAddress.getEditText()).getText().toString();
    }

    private void setDataEditText() {
        binding.txtInputName.getEditText().setText(user.getName());
        binding.txtInputPhoneNumber.getEditText().setText(user.getPhoneNumber() + "");
        binding.txtInputAddress.getEditText().setText(user.getAddress() + "");
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

    private void getLocationByLaLng(LatLng latLng) {
        try {
            List<Address> geocoder = new Geocoder(this).getFromLocation(latLng.latitude, latLng.longitude, 15);
            assert geocoder != null;
            if (!geocoder.isEmpty()) {
                binding.txtInputAddress.getEditText().setText(geocoder.get(0).getAddressLine(0)+ "");
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.clear();
                MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                markerOptions.title("Địa điểm giao hàng tới");
                googleMap.addMarker(markerOptions);
            }

        } catch (IOException ignored) {

        }
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
}