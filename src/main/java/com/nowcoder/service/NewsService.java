package com.nowcoder.service;

import com.nowcoder.dao.NewsDao;
import com.nowcoder.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 16:12 2019/3/28
 */
@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    public List<News> getlastNews(int userId, int offset,int limit){
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }


}
