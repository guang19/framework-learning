package com.github.guang19.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yangguang
 * @date 2020/2/13
 * @description <p></p>
 */
@ApiModel("用户实体类")
public class User
{
    //private字段无效
    @ApiModelProperty("用户名属性")
    public String username;

    @ApiModelProperty("用户描述")
    public String description;
}
