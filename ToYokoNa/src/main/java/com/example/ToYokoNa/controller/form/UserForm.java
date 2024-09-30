package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import com.example.ToYokoNa.Validation.Unique;
import com.example.ToYokoNa.repository.UserRepository;
import com.example.ToYokoNa.repository.entity.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Component
public class UserForm {

    //ログインorユーザ登録・編集でバリデーションの使用を分けたいのでグループ分け
    public static interface UserLogin{}
    public static interface UserCreate{}
    public static interface UserEdit{}

    private int id;

    @CheckBlank(message = "アカウントを入力してください", groups = {UserLogin.class, UserCreate.class, UserEdit.class})
    @Unique(groups = {UserCreate.class}) //重複チェック
    @Size(min = 6, max = 20, message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class, UserEdit.class})
    @Pattern(regexp= "^[A-Za-z0-9]+$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class, UserEdit.class})
    private String account;

    @CheckBlank(message = "パスワードを入力してください", groups = {UserLogin.class, UserCreate.class})
    @Size(min = 6, max = 20, message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class})
    @Pattern(regexp = "^[A-Za-z]+$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserCreate.class})
    private String password;
    private String passCheck;

    @AssertTrue(message = "パスワードと確認用パスワードが一致しません", groups = {UserCreate.class, UserEdit.class})
    private boolean isSamePassword() {
        return Objects.equals(password, passCheck);
    }

    @CheckBlank(message = "氏名を入力してください", groups = {UserCreate.class, UserEdit.class})
    @Size(max = 10, message = "氏名は10文字以下で入力してください", groups = {UserCreate.class, UserEdit.class})
    private String name;

    @NotNull(message = "支社を選択してください", groups = {UserCreate.class, UserEdit.class})
    private Integer branchId;

    @NotNull(message = "部署を選択してください", groups = {UserCreate.class, UserEdit.class})
    private Integer departmentId;

    @AssertTrue(message = "支社と部署の組み合わせが不正です", groups = {UserCreate.class, UserEdit.class})
    public boolean isRelationValid() {
        if (branchId == null || departmentId == null) {
            return true;
        }
        if (branchId == 1) { //支社が本社の場合の正しい組み合わせ
            return (departmentId == 1 || departmentId == 2);
        } else { //支社が本社以外の場合の正しい組み合わせ
            return (departmentId == 3 || departmentId == 4);
        }
    }

    @AssertTrue(message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserEdit.class})
    public boolean isPasswordValid() {
        //  パスワードが2つとも何も入力されていなければバリデーションはかけない
        if (password.isEmpty() && passCheck.isEmpty()) {
            return true;
        }
        if (password.length() >= 6 && password.length() <= 20 && password.matches("^[A-Za-z]+$")) {
            return true;
        }
        return false;
    }

    private Integer isStopped;
    private Date createdDate;
    private Date updatedDate;
}