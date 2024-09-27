package com.example.ToYokoNa.service;

import com.example.ToYokoNa.controller.form.CommentForm;
import com.example.ToYokoNa.controller.form.UserForm;
import com.example.ToYokoNa.repository.CommentRepository;
import com.example.ToYokoNa.repository.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public void saveComment(UserForm loginUser, CommentForm commentForm) {
        commentForm.setUserId(loginUser.getId());
        Comment comment = setComment(commentForm);
        commentRepository.save(comment);
    }

    private Comment setComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setText(commentForm.getText());
        comment.setUserId(commentForm.getUserId());
        comment.setMessageId(commentForm.getMessageId());
        comment.setCreatedDate(commentForm.getCreatedDate());
        comment.setUpdatedDate(commentForm.getUpdatedDate());
        return comment;
    }
}
