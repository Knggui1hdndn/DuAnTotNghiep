package com.knd.duantotnghiep.duantotnghiep.respository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.knd.duantotnghiep.duantotnghiep.models.ChatMessage;
import com.knd.duantotnghiep.duantotnghiep.models.User;
import com.knd.duantotnghiep.duantotnghiep.remote.AuthApi;
import com.knd.duantotnghiep.duantotnghiep.remote.ChatApi;
import com.knd.duantotnghiep.duantotnghiep.utils.ApiCallBack;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;
import com.knd.duantotnghiep.duantotnghiep.utils.TokenManager;
import com.knd.duantotnghiep.duantotnghiep.utils.UserPreferencesManager;

import java.util.List;

import javax.inject.Inject;

import okhttp3.MultipartBody;

public class ChatRepository {
    private ChatApi chatApi;
    private UserPreferencesManager userPreferencesManager;
    private final MutableLiveData<NetworkResult<List<ChatMessage>>> _chat = new MutableLiveData<>();

    public MutableLiveData<NetworkResult<List<ChatMessage>>> chat = _chat;

    private final MutableLiveData<NetworkResult<List<String>>> _images = new MutableLiveData<>();

    public MutableLiveData<NetworkResult<List<String>>> images = _images;

    @Inject
    public ChatRepository(ChatApi chatApi, UserPreferencesManager userPreferencesManager) {
        this.chatApi = chatApi;
        this.userPreferencesManager = userPreferencesManager;
    }

    public void getListChat(String id) {
        NetworkResult.handleCallApi(chatApi.getListChats(id ),_chat,null);
    }

    public void addImageChats(List<MultipartBody.Part> partList) {
        NetworkResult.handleCallApi(chatApi.addImage(partList),_images,null);
    }
}
