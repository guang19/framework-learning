package com.guang19.springmvc.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description : TODO
 * @Author :    yangguang
 * @Date :      2019/12/25
 */
@Controller
public class TestController {

    @ResponseBody
    @RequestMapping("/test")
    public String test()
    {
        return "hello spring mvc";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public String test2()
    {
        return "hello spring mvc";
    }
}
