package com.nowcoder.controller;

import com.nowcoder.model.*;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.TengXunService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 15:39 2019/4/11
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);
    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    TengXunService tengXunService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path={"/news/{newsId}"},method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId") int newsId,
                             Model model){
        try {
            News news=newsService.getNewsById(newsId);
            //显示评论
            if(news!=null){
                List<Comment> comments=commentService.getCommentEntity(news.getId(),EntityType.ENTITY_NEWS);
                List<ViewObject> commentVOs=new ArrayList<ViewObject>();

                for(Comment comment:comments){
                    ViewObject commentVO=new ViewObject();
                    commentVO.set("comment",comment);
                    commentVO.set("user",userService.getUserById(comment.getUserId()));
                    commentVOs.add(commentVO);
                }
                model.addAttribute("comments",commentVOs);
            }
            model.addAttribute("news",news);
            model.addAttribute("owner",userService.getUserById(news.getUserId()));


        } catch (Exception e) {
            logger.error("获取资讯信息失败"+e.getMessage());
        }
        return  "detail";
    }


    @RequestMapping(path={"/image"},method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                           HttpServletResponse response){

        try {
            response.setContentType("image/jepg");
            StreamUtils.copy(new FileInputStream(new File(TouTiaoUtil.IMAGE_DIR+imageName)),response.getOutputStream());
            //服务器获取图片
            // String path = "https://toutiao-1259039522.cos.ap-beijing.myqcloud.com/";
            //StreamUtils.copy(new FileInputStream(new File(path+imageName)),response.getOutputStream());

        } catch (IOException e) {
            logger.error("读取图片错误"+imageName+e.getMessage());
        }
    }

    @RequestMapping(path={"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try {
            //服务器上传图片
            //String fileUrl=tengXunService.Upload(file);
            String fileUrl=newsService.saveImage(file);
            if(fileUrl==null){
                return TouTiaoUtil.getJSONString(1,"上传图片失败");
            }
            return  TouTiaoUtil.getJSONString(0,fileUrl);

        } catch (Exception e) {
            logger.error("上传图片失败"+e.getMessage());
            return  TouTiaoUtil.getJSONString(1,"上传失败");
        }

    }

    @RequestMapping(path={"/user/addNews"},method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link){

        try {
            News news=new News();
            news.setTitle(title);
            news.setImage(image);
            news.setLink(link);
            news.setCreatedDate(new Date());
            if(hostHolder.getUser()!=null){
                news.setUserId(hostHolder.getUser().getId());
            }else{
                news.setUserId(0);
            }
            newsService.addNews(news);
            return  TouTiaoUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("添加资讯失败"+e.getMessage());
            return TouTiaoUtil.getJSONString(1,"发布失败");
        }
    }

    @RequestMapping(path={"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                             @RequestParam("content") String content){

        try {
            //增加评论
            Comment comment=new Comment();
            comment.setUserId(hostHolder.getUser().getId());
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityId(newsId);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setStatus(0);
            commentService.addComment(comment);

            //更新评论数量
            int count=commentService.getCommentCount(newsId,EntityType.ENTITY_NEWS);
            newsService.updateCommentCount(comment.getEntityId(),count);
        } catch (Exception e) {
            logger.error("添加评论出错"+e.getMessage());
        }
        return "redirect:/news/" + String.valueOf(newsId);
    }






}
