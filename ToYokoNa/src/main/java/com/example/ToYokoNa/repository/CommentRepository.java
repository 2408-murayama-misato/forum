package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
