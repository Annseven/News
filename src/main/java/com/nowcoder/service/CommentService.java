package com.nowcoder.service;

import com.nowcoder.dao.CommentDao;
import com.nowcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 17:41 2019/4/17
 */
@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    public List<Comment> getCommentEntity(int entityId,int entityType ){
        return commentDao.selectByEntity(entityId, entityType);

    }

    public void addComment(Comment comment){
        commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId,int entityType){

        return  commentDao.getCommentCount(entityId,entityType);
    }
}
