package com.nowcoder.service;

import com.nowcoder.dao.UserDao;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 16:20 2019/3/28
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getUserById(int id){
        return  userDao.selectById(id);
    }
}
