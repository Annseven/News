package com.nowcoder.controller;

import com.nowcoder.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 14:12 2019/3/27
 */
@Controller
public class FirstController {
    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public  String index(){
        return "Hello,Lee";
    }

    @RequestMapping(value={"/profile/{userName}/{userId}"})
    @ResponseBody
    public  String profile(@PathVariable("userName") String userName,
                           @PathVariable("userId") String userId,
                           @RequestParam(value="type",defaultValue = "1") String type,
                           @RequestParam(value="key",defaultValue = "nowcoder") String key){
        return String.format("UserName{%s},UserId{%s},type{%s},key{%s}",userName,userId,type,key);

    }

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

        model.addAttribute("user", new User("Jim"));

        return "news";
    }
}
