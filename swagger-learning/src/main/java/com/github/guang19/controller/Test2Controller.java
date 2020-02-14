package com.github.guang19.controller;

import com.github.guang19.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang
 * @date 2020/2/13
 * @description <p>测试2controller</p>
 */
@Api(tags = "测试二接口")
@Controller
public class Test2Controller
{
    @ApiOperation("方法2")
    @GetMapping("/test2")
    public String test2(@ApiParam("用户") @RequestBody User user)
    {
        return "test2";
    }
}
