package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.UserMessageForm;
import com.example.ToYokoNa.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    /*
    Top画面表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        List<UserMessageForm> messages = messageService.findMessages();
        mav.addObject("messages", messages);
        mav.setViewName("/top");
        return mav;
    }

}
