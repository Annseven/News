package com.nowcoder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: AnNing
 * @Description:
 * @Date: Create in 18:08 2019/3/27
 */
@Controller
public class SettingController {

    @RequestMapping("/setting")
    @ResponseBody
    public String setting(){

        return "setting is ok!";
    }
}
