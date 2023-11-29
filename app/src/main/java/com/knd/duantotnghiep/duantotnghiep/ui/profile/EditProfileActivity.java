package com.knd.duantotnghiep.duantotnghiep.ui.profile;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

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

        setDataEditText();
    }

    @SuppressLint("SetTextI18n")
    private void setDataEditText() {
        setTextEditText(binding.txtInputName,user.getName());
        setTextEditText(binding.txtInputPhoneNumber,user.getPhoneNumber()+"");
        setTextEditText(binding.txtInputAddress,user.getAddress()+"");
        setTextEditText(binding.txtInputEmail,user.getEmail()+"");
        Utils.loadImage(binding.avatar, user.getAvatar());
    }

    private void setTextEditText(TextInputLayout txtInputName,String s) {
        txtInputName.getEditText().setText(s);
    }

    @Override
    protected void initObserver() {
        userRepository.editUser.observe(this, result -> ApiCallBack.handleResult(result, new ApiCallBack.HandleResult<User>() {
            @Override
            public void handleSuccess(User data) {
                new UserPreferencesManager(EditProfileActivity.this).saveUser(data);
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
            updateProfile();
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