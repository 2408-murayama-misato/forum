package com.example.ToYokoNa.controller;

import com.example.ToYokoNa.controller.form.BranchForm;
import com.example.ToYokoNa.controller.form.DepartmentForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.entity.User;
import com.example.ToYokoNa.service.BranchService;
import com.example.ToYokoNa.service.DepartmentService;
import com.example.ToYokoNa.service.UserService;
import com.example.ToYokoNa.utils.CipherUtil;
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
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    BranchService branchService;
    @Autowired
    DepartmentService departmentService;
    // ログイン画面表示
    @GetMapping("/userLogin")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = new UserForm();
        // 遷移先
        mav.setViewName("userLogin");
        // 空のフォームの送信
        mav.addObject("userForm", userForm);
        return mav;
    }
    /*
     * ユーザーログイン処理
     */
    @PostMapping("/userLogin")
    public ModelAndView login(@ModelAttribute("userForm") @Validated({ UserForm.UserLogin.class }) UserForm userForm,
                              BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/userLogin");
        }
        // パスワードを暗号化
        String encPassword = CipherUtil.encrypt(userForm.getPassword());
        // 暗号化した物をuserFormにセット
        userForm.setPassword(encPassword);
        // try文の中と処理後にもuserDataオブジェクトを使用できるように先に作成
        UserForm userData;
        try {
            userData = userService.selectUser(userForm);
            // もしユーザが停止している場合,もしくはユーザ情報が存在しない場合
            if (userData.getIsStopped() == 1) {
                // 例外を投げてcatchで処理をする
                throw new Exception("ログインに失敗しました");
            }
        } catch (Exception e) {
            // 設定したメッセージを取得しerrorMessageとしてhtmlに渡す
            mav.addObject("errorMessage", e.getMessage());
            mav.setViewName("/userLogin");
            return mav;
        }
        // セッションに値をセット
        session.setAttribute("loginUser", userData);
        // topにリダイレクト
        mav.setViewName("redirect:/");
        return mav;
    }

    /*
     * ユーザー管理画面表示(ユーザー全取得)
     */
    @GetMapping("/userManage")
    public ModelAndView userManage() {
        ModelAndView mav = new ModelAndView();
        List<UserForm> users = userService.findAllUser();
        mav.setViewName("/userManage");
        mav.addObject("users", users);
        return mav;
    }

    /*
     * ユーザー復活・停止機能
     */
    @PutMapping("/{id}")
    public ModelAndView changeIsStopped(@PathVariable("id") int id) {
        // 対象のユーザを取得
        UserForm userData = userService.findById(id);
        // ユーザのisStoppedの値を変更させる
        if (userData.getIsStopped() == 1) {
            userData.setIsStopped(0);
        } else {
            userData.setIsStopped(1);
        }
        // 値を変更させたらデータを保存させる
        userService.saveUser(userData);
        return new ModelAndView("redirect:/userManage");
    }

    /*
    ユーザー編集機能
     */
    @GetMapping("/userEdit/{id}")
    public ModelAndView userEdit (@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        UserForm user = userService.findUser(id);
        mav.addObject("user", user);
        mav.setViewName("/userEdit");
        return mav;
    }

    /*
     * ユーザー新規登録画面表示
     */
    @GetMapping("/userCreate")
    public ModelAndView userCreate(@ModelAttribute("userForm") UserForm userForm) {
        ModelAndView mav = new ModelAndView();
        // 支社と部署名をDBから持ってきたいので取得。
        List<BranchForm> branches = branchService.findAllBranches();
        List<DepartmentForm> departments = departmentService.findAllDepartments();
        mav.addObject("branches", branches);
        mav.addObject("departments", departments);
        mav.setViewName("/userCreate");
        return mav;
    }
    /*
     * 新規ユーザー登録処理
     */
    @PostMapping("/userCreate")
    public ModelAndView userCreate(@ModelAttribute("userForm") @Validated({ UserForm.UserCreate.class}) UserForm userForm,
                                   BindingResult result) {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        // ユーザー停止状態を0の稼働状態にする
        userForm.setIsStopped(0);
        // エラー処理
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0 ) {
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("userForm", userForm);
            // 部署と支店情報が選択肢からなくなってしまうので2つもmavにaddする
            mav.addObject("departments", departmentService.findAllDepartments());
            mav.addObject("branches", branchService.findAllBranches());
            mav.setViewName("/userCreate");
        } else {
            // パスワードを暗号化する
            userForm.setPassword(CipherUtil.encrypt(userForm.getPassword()));
            userService.saveUser(userForm);
            mav.setViewName("redirect:/userManage");
        }
        return mav;
    }
}