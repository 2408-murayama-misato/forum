package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.MessageForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.controller.form.UserMessageForm;
import com.example.ToYokoNa.repository.MessageRepository;
import com.example.ToYokoNa.repository.entity.Message;
import com.example.ToYokoNa.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    /*
    投稿取得処理
     */
    public List<UserMessageForm> findMessages() {
        int limit = 1000;
        List<Message> results = messageRepository.findAllByOrderByCreateDateDesc(limit);
        List<UserMessageForm> messages = setUserMessageForm(results);
        return messages;
    }

    private List<UserMessageForm> setUserMessageForm(List<Message> results) {
        List<UserMessageForm> messages = new ArrayList<>();
        for (Message message : results) {
            UserMessageForm userMessageForm = new UserMessageForm();
            userMessageForm.setId(message.getId());
            userMessageForm.setText(message.getText());
            userMessageForm.setTitle(message.getTitle());
            userMessageForm.setUserId(message.getUserId());
            userMessageForm.setUserName(message.getUser().getName());
            userMessageForm.setUpdatedDate(message.getUpdatedDate());
            userMessageForm.setCategory(message.getCategory());
            messages.add(userMessageForm);
        }
        return messages;
    }

    public void save(MessageForm messageForm, UserForm loginUser) {
        messageForm.setUserId(loginUser.getId());
        Message message = setMessage(messageForm);
        messageRepository.save(message);
    }

    private Message setMessage(MessageForm messageForm) {
        Message message = new Message();
        message.setId(messageForm.getId());
        message.setTitle(messageForm.getTitle());
        message.setText(messageForm.getText());
        message.setCategory(messageForm.getCategory());
        message.setUserId(messageForm.getUserId());
        return message;
    }
}
