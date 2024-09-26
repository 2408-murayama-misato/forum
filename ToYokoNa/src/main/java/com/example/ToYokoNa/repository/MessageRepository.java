package com.example.ToYokoNa.repository;

import com.example.ToYokoNa.repository.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query("SELECT m FROM Message m ORDER BY updatedDate DESC LIMIT :limit")
    public List<Message> findAllByOrderByUpdateDesc(@Param("limit") int limit);

}
