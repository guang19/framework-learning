package com.github.guang19.controller;

import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang
 * @date 2020/2/12
 * @description <p>测试controller</p>
 */
@Api(tags = "测试一接口")
@Controller
public class TestController
{

    @ApiResponses({@ApiResponse(code = 200,message = "返回正常结果"),@ApiResponse(code = 404,message = "资源不存在")})
    @ApiOperation("test方法")
    @GetMapping("/test")
    public String test(@ApiParam("用户名") String name)
    {
        return "hello world";
    }
}
