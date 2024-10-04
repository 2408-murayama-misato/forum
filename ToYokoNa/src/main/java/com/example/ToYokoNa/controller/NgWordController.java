package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.NgWordForm;
import com.example.ToYokoNa.service.NgWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NgWordController {
    @Autowired
    NgWordService ngWordService;

    //    NGワード設定画面遷移
    @GetMapping("/ngWord")
    public ModelAndView ngWord () {
        ModelAndView mav = new ModelAndView();
        List<NgWordForm> ngWordForms = ngWordService.findAllNgWords();
        mav.addObject("ngWords", ngWordForms);
        mav.setViewName("/ngWord");
        return mav;

    }
    //    NGワード登録
    @PostMapping("/add/ngWord")
    public ModelAndView addNgWord(@ModelAttribute("ngWord") NgWordForm ngWordForm) {
        ModelAndView mav = new ModelAndView();
        ngWordService.saveNgWord(ngWordForm);
        mav.setViewName("redirect:/ngWord");
        return mav;
    }

//    NGワード削除
    @DeleteMapping("deleteNgWord/{id}")
    public ModelAndView deleteNgWord(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        ngWordService.deleteNgWord(id);
        mav.setViewName("redirect:/ngWord");
        return mav;
    }

//    NGワード編集
    @PutMapping("/edit/{id}")
    public ModelAndView editNgWord(@ModelAttribute ("ngWord") NgWordForm ngWordForm){
        ModelAndView mav = new ModelAndView();
        ngWordService.saveNgWord(ngWordForm);
        mav.setViewName("redirect:/ngWord");
        return mav;
    }
}
