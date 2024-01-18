package com.knd.duantotnghiep.duantotnghiep.ui.dialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.knd.duantotnghiep.duantotnghiep.models.ChatMessage;
import com.knd.duantotnghiep.duantotnghiep.respository.ChatRepository;
import com.knd.duantotnghiep.duantotnghiep.respository.ProductRepository;
import com.knd.duantotnghiep.duantotnghiep.utils.NetworkResult;

import java.io.Closeable;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import okhttp3.MultipartBody;

@HiltViewModel
public class ChatViewModel extends ViewModel {
    private ChatRepository chatRepository;
    public LiveData<NetworkResult<List<ChatMessage>>> listChat;
    public LiveData<NetworkResult<List<String>>> images;

    @Inject
    public ChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
        listChat = chatRepository.chat;
        images = chatRepository.images;
    }

    public void getListChats(String id) {
        chatRepository.getListChat(id);

    }

    public void addImagesChat(List<MultipartBody.Part> partList) {
        chatRepository.addImageChats(partList);

    }
}
