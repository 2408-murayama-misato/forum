package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        user.setPassword(result.getPassword());
        user.setAccount(result.getAccount());
        user.setName(result.getName());
        user.setBranchId(result.getBranchId());
        user.setDepartmentId(result.getDepartmentId());
        user.setIsStopped(result.getIsStopped());
        return user;
    }
    /*
     * ユーザー管理画面表示
     */
    public List<UserForm> findAllUser() {
        List<User> results = userRepository.findAll();
        List<UserForm> users = setUserForm(results);
        return users;
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setName(result.getName());
            user.setBranchId(result.getBranchId());
            user.setDepartmentId(result.getDepartmentId());
            user.setIsStopped(result.getIsStopped());
            users.add(user);
        }
        return users;
    }

    /*
     * ユーザ情報の更新(ユーザの稼働状態と新規登録)
     */
    public void saveUser(UserForm userForm) {
        User saveUser = setUserEntity(userForm);
        userRepository.save(saveUser);
    }

    private User setUserEntity(UserForm userForm) {
        User user = new User();
        user.setId(userForm.getId());
        user.setPassword(userForm.getPassword());
        user.setAccount(userForm.getAccount());
        user.setName(userForm.getName());
        user.setBranchId(userForm.getBranchId());
        user.setDepartmentId(userForm.getDepartmentId());
        user.setIsStopped(userForm.getIsStopped());
        user.setUpdatedDate(new Date());
        return user;
    }

    public UserForm findUser(int id) {
        User result = userRepository.findById(id).orElse(null);
        UserForm user = setUserForm(result);
        return user;
    }

    public List<UserForm> findAllUserByAccount(String account) {
        List<User> results = userRepository.findAllByAccount(account);
        List<UserForm> users = setUserForm(results);
        return users;
    }
}
