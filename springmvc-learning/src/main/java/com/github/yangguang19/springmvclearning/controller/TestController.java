package com.github.yangguang19.springmvclearning.controller;

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
    public void test()
    {
        System.out.println("spring mvc test");
    }
}
