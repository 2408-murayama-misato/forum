package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByAccountAndPassword(String account, String password);
}
