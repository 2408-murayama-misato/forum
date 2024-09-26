package com.example.ToYokoNa.controller.form;

import com.example.ToYokoNa.Validation.CheckBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;

    @CheckBlank(message = "アカウントを入力してください")
    private String account;

    @CheckBlank(message = "パスワードを入力してください")
    private String password;

    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;
    private Date createdDate;
    private Date updatedDate;
}
