package com.nowcoder.controller;

import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.TouTiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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


    @RequestMapping(path={"/image"},method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                           HttpServletResponse response){

        try {
            response.setContentType("image/jepg");
            StreamUtils.copy(new FileInputStream(new File(TouTiaoUtil.IMAGE_DIR+imageName)),response.getOutputStream());
        } catch (IOException e) {
            logger.error("读取图片错误"+imageName+e.getMessage());
        }
    }

    @RequestMapping(path={"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file){
        try {
            String fileUrl=newsService.saveImage(file);
            if(fileUrl==null){
                return TouTiaoUtil.getJSONString(1,"上传图片失败");
            }
            return  TouTiaoUtil.getJSONString(0,fileUrl);

        } catch (IOException e) {
            logger.error("上传图片失败"+e.getMessage());
            return  TouTiaoUtil.getJSONString(1,"上传失败");
        }

    }


}
