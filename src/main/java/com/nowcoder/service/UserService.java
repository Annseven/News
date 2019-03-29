package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDao;
import com.nowcoder.dao.UserDao;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 16:20 2019/3/28
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;

    public Map<String,Object> register(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        //判断用户名密码是否为空
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return  map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
        }
       //判断用户名是否已经注册
        User user=userDao.selectByName(username);

        if(user!=null){
            map.put("msgname","用户名已经被注册");
            return  map;
        }
       //密码加密
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        String head=String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(TouTiaoUtil.MD5(password+user.getSalt()));
        userDao.addUser(user);

        //注册后登录
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);

        return map;
    }


    public Map<String,Object> login(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        //判断用户名密码是否为空
        if(StringUtils.isEmpty(username)){
            map.put("msgname","用户名不能为空");
            return  map;
        }
        if(StringUtils.isEmpty(password)){
            map.put("msgpwd","密码不能为空");
        }
        //判断用户名是否存在
        User user=userDao.selectByName(username);

        if(user==null){
            map.put("msgname","用户名不存在");
            return  map;
        }

        password=TouTiaoUtil.MD5(password+user.getSalt());

        if(!password.equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
        }

        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return  map;
    }

    public String addLoginTicket(int userId){
        LoginTicket ticket=new LoginTicket();
        ticket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replace("-"," "));
        loginTicketDao.addTicket(ticket);
        return  ticket.getTicket();
    }

    public User getUserById(int id){
        return  userDao.selectById(id);
    }
}
