package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.service.NgWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NgWordController {
    @Autowired
    NgWordService ngWordService;

    //    NGワード設定画面遷移
    @GetMapping("/ngWord")
    public ModelAndView ngWord () {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/ngWord");
        return mav;

    }
    //    NGワード登録
    @GetMapping("/add/ngWord")
    public ModelAndView addNgWord(@RequestParam("ngWord") String content) {
        ModelAndView mav = new ModelAndView();
        ngWordService.saveNgWord(content);
        mav.setViewName("redirect:/");
        return mav;
    }
}
