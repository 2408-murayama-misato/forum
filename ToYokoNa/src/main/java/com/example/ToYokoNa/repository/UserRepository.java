package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);

    User findByAccount(String account);
}