package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.knd.duantotnghiep.duantotnghiep.R;
import com.knd.duantotnghiep.duantotnghiep.core.BaseActivity;
import com.knd.duantotnghiep.duantotnghiep.core.BaseBottomSheetFrg;
import com.knd.duantotnghiep.duantotnghiep.databinding.FragmentChatBinding;
import com.knd.duantotnghiep.duantotnghiep.models.ChatMessage;
import com.knd.duantotnghiep.duantotnghiep.ui.details.ImageEvaluateAdapter;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.FileProviderUtils;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.socket.client.Socket;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class ChatActivity extends BaseActivity<FragmentChatBinding> {
    @Inject
    public Socket socket;
    private final Gson gson = new Gson();
    private ChatViewModel chatViewModel;
    private final List<String> uris = new ArrayList<>();
    private final List<MultipartBody.Part> parts = new ArrayList<>();
    private final List<ChatMessage> chatMessages = new ArrayList<>();
    private ImageEvaluateAdapter evaluateAdapter;
    private final AdapterChat adapterChat = new AdapterChat();
    private ChatMessage currentChatMessage;
    private final ActivityResultLauncher<PickVisualMediaRequest> activityResult = registerForActivityResult(new ActivityResultContracts.PickMultipleVisualMedia(5), new ActivityResultCallback<List<Uri>>() {
        @Override
        public void onActivityResult(List<Uri> uri) {
            if (!uri.isEmpty()) {
                uri.forEach(i -> {
                    parts.add(createMultipartPart(FileProviderUtils.createFileFromUri(i, ChatActivity.this)));
                    uris.add(String.valueOf(i));
                });
                evaluateAdapter.setData(uris);
            }
        }
    });

    public MultipartBody.Part createMultipartPart(File file) {
        return MultipartBody.Part.createFormData("images", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
    }


    @Override
    public FragmentChatBinding getViewBinding() {
        return FragmentChatBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        socket.connect();
        setUpToolBar(binding.textView35,true,getDrawable(R.drawable.baseline_arrow_back_ios_24));
        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatViewModel.getListChats(new UserPreferencesManager(this).getCurrentUser().get_id());
        evaluateAdapter = new ImageEvaluateAdapter();
        adapterChat.setData(chatMessages);
        binding.mRcy.setAdapter(adapterChat);
        binding.mRcyImage.setAdapter(evaluateAdapter);
        binding.message.setEndIconOnClickListener(view -> sendMessage());
        binding.message.setStartIconOnClickListener(view -> startImagePicker());
        socket.on(Socket.EVENT_CONNECT, args -> {
        });
        socket.on(Socket.EVENT_DISCONNECT, args -> {
        });
        socket.on("receive_message", args -> {
           runOnUiThread(() -> {
              try {
                  handleReceivedMessage(args[0].toString());
                  binding.mRcy.smoothScrollToPosition(chatMessages.size() - 1);

              }catch (Exception e) {}
           });
        });
        chatViewModel.listChat.observe(this, this::handleChatListResult);
        chatViewModel.images.observe(this, this::handleImagesResult);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }

    private void startImagePicker() {
        activityResult.launch(new PickVisualMediaRequest());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void sendMessage() {
        String message = Objects.requireNonNull(binding.message.getEditText()).getText().toString();
        currentChatMessage = new ChatMessage();

        if (!message.trim().isEmpty()) {
            currentChatMessage.setSender(new UserPreferencesManager(this).getCurrentUser().get_id());
            currentChatMessage.setMessage(message);

        }
        if (uris.isEmpty()) {
            sendChatMessage();
            binding.message.getEditText().setText("");
            chatMessages.add(currentChatMessage);
            adapterChat.setData(chatMessages);
            binding.mRcy.smoothScrollToPosition(chatMessages.size() - 1);
        } else {
            chatViewModel.addImagesChat(parts);
        }
    }

    private void sendChatMessage() {
        try {
            socket.emit("send_message", new JSONObject(gson.toJson(currentChatMessage)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleReceivedMessage(String message) {
        chatMessages.add(gson.fromJson(message, ChatMessage.class));
        adapterChat.setData(chatMessages);
    }

    private void handleChatListResult(NetworkResult<List<ChatMessage>> listNetworkResult) {
        ApiCallBack.handleResult(listNetworkResult, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(List<ChatMessage> data) {
                chatMessages.addAll(data);
                adapterChat.setData(chatMessages);
                binding.mRcy.scrollToPosition(data.size() - 1);
            }

            @Override
            public void handleError(String error) {
                // Handle error
            }

            @Override
            public void handleLoading() {
                // Handle loading
            }
        });
    }

    private void handleImagesResult(NetworkResult<List<String>> images) {
        ApiCallBack.handleResult(images, new ApiCallBack.HandleResult<>() {
            @Override
            public void handleSuccess(List<String> images) {
                currentChatMessage.setUrl(images);
                chatMessages.add(currentChatMessage);
                adapterChat.setData(chatMessages);
                uris.clear();
                parts.clear();
                evaluateAdapter.setData(uris);
                sendChatMessage();
                binding.message.getEditText().setText("");
                FileProviderUtils.deleteCache(ChatActivity.this);
            }

            @Override
            public void handleError(String error) {
                // Handle error
            }

            @Override
            public void handleLoading() {
                // Handle loading
            }
        });
    }
}
