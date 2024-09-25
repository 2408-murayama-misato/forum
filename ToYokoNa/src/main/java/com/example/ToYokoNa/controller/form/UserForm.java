package com.example.ToYokoNa.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;
    private String account;
    private String password;
    private String name;
    private Integer branchId;
    private Integer departmentId;
    private Integer isStopped;
    private Date createdDate;
    private Date updatedDate;
}
