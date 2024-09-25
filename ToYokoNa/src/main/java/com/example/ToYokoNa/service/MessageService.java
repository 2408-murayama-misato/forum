package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.MessageForm;
import com.example.ToYokoNa.repository.MessageRepository;
import com.example.ToYokoNa.repository.entity.Message;
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
    public List<MessageForm> findMessages() {
        List<Message> results = messageRepository.findAll();
        List<MessageForm> messages = setMessageForm(results);
        return messages;
    }

    private List<MessageForm> setMessageForm(List<Message> results) {
        List<MessageForm> messages = new ArrayList<>();
        for (Message message : results) {
            MessageForm messageForm = new MessageForm();
            messageForm.setId(message.getId());
            messageForm.setText(message.getText());
            messageForm.setUserId(message.getUserId());

        }

        return messages;
    }
}
