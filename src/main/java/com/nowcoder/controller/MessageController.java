package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Message;
import com.nowcoder.model.User;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 15:19 2019/4/18
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @RequestMapping(path={"/msg/detail"},method = {RequestMethod.GET})
    public String conversationDetail(Model model,@RequestParam("conversationId") String conversationId){

        try {
            List<ViewObject> messages=new ArrayList<>();
            List<Message> conversationList=messageService.getConversationDetail(conversationId,0,10);
            for(Message message:conversationList){
                ViewObject vo=new ViewObject();
                vo.set("message",message);
                User user=userService.getUserById(message.getFromId());
                if(user==null){
                    continue;
                }
                vo.set("headUrl",user.getHeadUrl());
                vo.set("userName",user.getName());
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        } catch (Exception e) {
            logger.error("获取站内信失败"+e.getMessage());
        }
        return  "letterDetail";

    }
    @RequestMapping(path={"/msg/list"},method = {RequestMethod.GET})
    public String getConversationList(Model model){

        try {
            int localUserId=hostHolder.getUser().getId();
            List<ViewObject> conversations=new ArrayList<>();
            List<Message> conversationList=messageService.getConversationList(localUserId,0,10);
            for(Message message:conversationList){
                ViewObject vo=new ViewObject();
                vo.set("conversation",message);
                int targetId = message.getFromId() == localUserId ? message.getToId() : message.getFromId();
                User user=userService.getUserById(targetId);
                vo.set("headUrl",user.getHeadUrl());
                vo.set("userName",user.getName());
                vo.set("targetId",targetId);
                vo.set("totalCount",message.getId());
                vo.set("unreadCount",messageService.getUnreadCount(localUserId,message.getConversationId()));
                conversations.add(vo);
            }
            model.addAttribute("conversations",conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败"+e.getMessage());
        }
        return "letter";
    }



        @RequestMapping(path={"/msg/addMessage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("content") String content,
                             @RequestParam("toId") int toId,
                             @RequestParam("fromId") int fromId){
        Message message=new Message();
        message.setContent(content);
        message.setFromId(fromId);
        message.setToId(toId);
        message.setFromId(fromId);
        message.setConversationId(fromId<toId?String.format("%d_%d",fromId,toId):String.format("%s_%d",toId,fromId));
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
        return TouTiaoUtil.getJSONString(message.getId());
    }



}
