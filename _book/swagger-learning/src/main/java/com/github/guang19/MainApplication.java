package com.github.guang19;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yangguang
 * @date 2020/2/12
 * @description <p>main</p>
 */

@EnableSwagger2
@SpringBootApplication
public class MainApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(MainApplication.class,args);
    }
}
