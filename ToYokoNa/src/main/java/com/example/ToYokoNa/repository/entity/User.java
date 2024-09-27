package com.example.ToYokoNa.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="account")
    private String account;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="branch_id")
    private Integer branchId;

    @Column(name="department_id")
    private Integer departmentId;

    @Column(name="is_stopped")
    private Integer isStopped;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="updated_date")
    private Date updatedDate;

    @OneToMany(mappedBy = "user")
    private List<Message> message;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;


}