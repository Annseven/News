package com.nowcoder.controller;

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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 14:37 2019/3/29
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="rember",defaultValue ="0" ) int rememberme,
                      HttpServletResponse response){

        try {

            Map<String, Object> map = userService.register(username, password);
            if (map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return  TouTiaoUtil.getJSONString(0,"注册成功");
            }else {
                return TouTiaoUtil.getJSONString(1,map);
            }


        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return TouTiaoUtil.getJSONString(1, "注册异常");
        }

    }


    @RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="rember",defaultValue ="0" ) int rememberme,
                      HttpServletResponse response){

        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")){
                //把ticket写到cookie中
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                // Cookie全量有效
                cookie.setPath("/");
                if (rememberme>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return  TouTiaoUtil.getJSONString(0,"登录成功");
            }else {
                return TouTiaoUtil.getJSONString(1,map);
            }
        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return TouTiaoUtil.getJSONString(1, "登录异常");
        }

    }



}