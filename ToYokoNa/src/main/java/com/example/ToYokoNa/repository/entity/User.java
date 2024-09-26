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

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "branch_id")
    private  int branchId;

    @Column(name = "department_id")
    private  int departmentId;

    @Column(name = "is_stopped")
    private  int isStopped;

    @Column(name = "created_date",insertable = false, updatable = false)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false)
    private Date updatedDate;

    @OneToMany(mappedBy = "user")
    private List<Message> message;

}
