package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="messages")
@Getter
@Setter
public class Message {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String category;

    @Column
    private  int userId;

    @Column(insertable = false, updatable = false)
    private Date createDate;

    @Column(insertable = false)
    private Date updateDate;



}
