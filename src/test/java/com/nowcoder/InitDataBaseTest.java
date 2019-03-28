package com.nowcoder;

import com.nowcoder.dao.NewsDao;
import com.nowcoder.dao.UserDao;
import com.nowcoder.model.News;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 14:18 2019/3/28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TouTiaoApplication.class)
public class InitDataBaseTest {

    @Autowired
    UserDao userDao;

    @Autowired
    NewsDao newsDao;



    @Test
    public void initData(){

        Random random=new Random();

        for (int i=0;i<11;i++){
            User user =new User();
            user.setPassword("");
            user.setSalt("");
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("User%d",i));
            userDao.addUser(user);

            user.setPassword("123456");
            userDao.updatePassword(user);

            News news=new News();
            news.setCommentCount(i);
            Date date=new Date();
            date.setTime(date.getTime()+1000*3600*5*i);
            news.setCreatedDate(date);
            news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
            news.setLikeCount(i+1);
            news.setUserId(i+1);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink(String.format("http://www.nowcoder.com/%d.html", i));

            newsDao.addNews(news);
        }

        Assert.assertEquals("123456",userDao.selectById(35).getPassword());
        userDao.deleteById(35);
        Assert.assertNull(userDao.selectById(35));

    }
}
