package com.example.ToYokoNa.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
public class UserMessageForm {

    private int id;

    private String title;

    private String text;

    private String category;

    private int userId;

    private Date createdDate;

    private Date updatedDate;

    private String userName;

    private Integer departmentId;

    private Integer branchId;

    private long seconds;

    private long minutes;

    private long hours;

    private long days;

    private long months;

    private long years;

}