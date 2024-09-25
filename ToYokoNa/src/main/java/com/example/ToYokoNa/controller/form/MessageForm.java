package com.example.ToYokoNa.controller.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageForm {
    private int id;

    private String title;

    private String text;

    private String category;

    private  int userId;

    private Date createDate;

    private Date updateDate;
}
