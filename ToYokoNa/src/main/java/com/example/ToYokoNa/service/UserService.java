package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * ログインユーザー情報取得(ログイン処理)
     */
    public UserForm selectUser(UserForm userForm) throws Exception {
        String account = userForm.getAccount();
        String password = userForm.getPassword();
        User result = userRepository.findByAccountAndPassword(account, password);
        if (result == null) {
            // Controllerにエラーを投げる
            throw new Exception("ログインに失敗しました");
        }
        UserForm user = setUserForm(result);
        return user;
    }

    private UserForm setUserForm(User result) {
        UserForm user = new UserForm();
        user.setId(result.getId());
        user.setAccount(result.getAccount());
        user.setName(result.getName());
        user.setBranchId(result.getBranchId());
        user.setDepartmentId(result.getDepartmentId());
        user.setIsStopped(result.getIsStopped());
        return user;
    }
}
