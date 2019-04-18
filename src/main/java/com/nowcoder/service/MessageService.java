package com.nowcoder.service;

import com.nowcoder.dao.MessageDao;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 15:13 2019/4/18
 */
@Service
public class MessageService {

    @Autowired
    MessageDao messageDao;

    public void addMessage(Message message){
         messageDao.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId,int offset,int limit){
        return  messageDao.getConversationDetail(conversationId,offset,limit);
    }

    public List<Message> getConversationList(int userId,int offset,int limit){
        return messageDao.getConversationList(userId,offset,limit);
    }

    public int getUnreadCount(int userId, String conversationId) {
        return messageDao.getConversationUnReadCount(userId, conversationId);
    }

}
