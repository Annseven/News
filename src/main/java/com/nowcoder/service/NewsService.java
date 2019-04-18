package com.nowcoder.service;

import com.nowcoder.dao.NewsDao;
import com.nowcoder.model.News;
import com.nowcoder.util.TouTiaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 16:12 2019/3/28
 */
@Service
public class NewsService {

    @Autowired
    NewsDao newsDao;

    public News getNewsById(int newsId){
        return  newsDao.getById(newsId);
    }

    public int addNews(News news){
       newsDao.addNews(news);
       return news.getId();
    }

    public List<News> getlastNews(int userId, int offset,int limit){
        return newsDao.selectByUserIdAndOffset(userId,offset,limit);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int dotPos=file.getOriginalFilename().lastIndexOf(".");
        if(dotPos<0){
            return  null;
        }
        String fileExt=file.getOriginalFilename().substring(
                dotPos+1).toLowerCase();
        if(!TouTiaoUtil.isFileAllowed(fileExt)){
             return null;
        }
        String  fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+fileExt;
        Files.copy(file.getInputStream(),new File(TouTiaoUtil.IMAGE_DIR+fileName).toPath(),StandardCopyOption.REPLACE_EXISTING);
        return TouTiaoUtil.TOUTIAO_DOAMIAN+"image?name="+fileName;
    }


    public void updateCommentCount(int newsId,int commentCount){
        newsDao.updateCommentCount(newsId,commentCount);
    }






}
