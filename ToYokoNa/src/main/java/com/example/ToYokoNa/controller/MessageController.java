package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.*;
import com.example.ToYokoNa.service.CommentService;
import com.example.ToYokoNa.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    private HttpSession session;

    @Autowired
    CommentService commentService;

    /*
    Top画面表示処理
     */
    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        List<UserMessageForm> messages = messageService.findALLMessages();
        List<UserCommentForm> comments = commentService.findAllComments();
        CommentForm commentForm = new CommentForm();
        mav.addObject("commentForm", commentForm);
        mav.addObject("comments", comments);
        mav.addObject("messages", messages);
        mav.addObject("errorMessages", session.getAttribute("errorMessages"));
        mav.addObject("loginUser", session.getAttribute("loginUser"));
        mav.setViewName("/top");
        // 管理者フィルターのエラーメッセージをsessionで渡しているので最後に削除してtopページ表示
        session.removeAttribute("errorMessages");
        return mav;
    }
    /*
    新規投稿画面遷移
     */
    @GetMapping("/newMessage")
    public ModelAndView newMessage(@ModelAttribute("messageForm") MessageForm messageForm) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("messageForm", messageForm);
        mav.setViewName("/newMessage");
        return mav;
    }
    /*
    投稿追加処理
     */
    @PostMapping("/addMessage")
    public ModelAndView addMessage(@ModelAttribute("messageForm")
                                       @Validated MessageForm messageForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0 ) {
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("messageForm", messageForm);
            mav.setViewName("redirect:/newMessage");
        } else {
            messageService.save(messageForm, loginUser);
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    /*
    投稿削除処理
     */
    @DeleteMapping("/deleteMessage/{id}")
    public ModelAndView deleteMessage(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        MessageForm message = messageService.findMessage(id);
        List<String> errorMessages = new ArrayList<>();
        if (loginUser.getId() == message.getUserId()) {
            messageService.deleteMessage(id);
        }
        mav.setViewName("redirect:/");
        return mav;
    }
}
