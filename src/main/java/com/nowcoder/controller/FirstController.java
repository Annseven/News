package com.nowcoder.controller;

import com.nowcoder.model.User;
import com.nowcoder.service.TouTiaoservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 14:12 2019/3/27
 */
@Controller
public class FirstController {

    @Autowired
    private TouTiaoservice touTiaoservice;

    @RequestMapping(path={"/index2"},method = {RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public String index2(HttpSession session){

        return "hello Anne,"+session.getAttribute("msg")+
                "<br>Say:"+touTiaoservice.say();

    }

    //url使用
    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public  String index(){
        return "Hello,Lee";
    }
    //参数的使用
    @RequestMapping(value={"/profile/{userName}/{userId}"})
    @ResponseBody
    public  String profile(@PathVariable("userName") String userName,
                           @PathVariable("userId") String userId,
                           @RequestParam(value="type",defaultValue = "1") String type,
                           @RequestParam(value="key",defaultValue = "nowcoder") String key){
        return String.format("UserName{%s},UserId{%s},type{%s},key{%s}",userName,userId,type,key);

    }

   //测试前后台数据传递
    @RequestMapping(path={"/vm"})
    public  String News(Model model){
        model.addAttribute("value1","anning");
        List<String> colors=Arrays.asList("RED","GREEN","BLUE","YELLOW");
        model.addAttribute("colors",colors);

        Map<String,String> map= new HashMap<String,String>();
        for (int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);

//        model.addAttribute("user", new User("Jim"));

        return "news";
    }

    //Request主要是参数的接，cooike读取
    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb= new StringBuilder();
        Enumeration<String> headernames=request.getHeaderNames();
        while(headernames.hasMoreElements()){
            String name=headernames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }

        for(Cookie cookie:request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }


        sb.append("getMethod:"+request.getMethod()+"<br>");
        sb.append("getpathInfo:"+request.getPathInfo()+"<br>");
        sb.append("getQueryString:"+request.getQueryString()+"<br>");
        sb.append("getRequest:"+request.getRequestURI()+"<br>");

        return  sb.toString();

    }

    //response主要是页面内容返回，cookie的下发
    @RequestMapping(path={"/response"})
    @ResponseBody
    public  String response(@CookieValue(value="nowcoderid",defaultValue ="a") String nowcodeid,
                            @RequestParam(value="key",defaultValue = "key") String key,
                            @RequestParam(value="value",defaultValue = "value") String value,
                            HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "NowCoderId from Cookie:"+nowcodeid;
    }
    //重定向
    @RequestMapping("/redirect/{code}")
    public String redict(@PathVariable("code") int code,
                         HttpSession session){

        session.setAttribute("msg","Jump from redict");
        return "redirect:/";
    }
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value="key",required = false) String key){
        if ("admin".equals(key)){
            return  "hello,admin";
        }
        throw new  IllegalArgumentException("Key错误！");
    }


}
