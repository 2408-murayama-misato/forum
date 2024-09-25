package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    // ログイン画面表示
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = new UserForm();
        // 遷移先
        mav.setViewName("/login");
        // 空のフォームの送信
        mav.addObject("userForm", userForm);
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute("userForm") UserForm userForm) {
        ModelAndView mav = new ModelAndView();
    }
}
