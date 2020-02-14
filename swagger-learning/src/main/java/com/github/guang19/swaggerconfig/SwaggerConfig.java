package com.github.guang19.swaggerconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @author yangguang
 * @date 2020/2/12
 * @description <p>配置swagger</p>
 */
@Configuration
public class SwaggerConfig
{
    /**
     * 配置 Docket
     * @return  Docket
     */
    @Bean
    public Docket docket(@Qualifier("myApiInfo")ApiInfo apiInfo,@Qualifier("environment") Environment environment)
    {
        boolean flag = environment.acceptsProfiles(Profiles.of("dev"));
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo).
                groupName("杨小光").
                enable(flag).
                select().
                apis(RequestHandlerSelectors.basePackage("com.github.guang19.controller")).
                paths(PathSelectors.ant("/test*/**")).
                build();
    }

    @Bean("myApiInfo")
    public ApiInfo apiInfo()
    {
        return new ApiInfo("杨小光的Api Doc","孤独的杨小光","v1.0","http://lyci.xyz",
                new Contact("杨小光","http://github.com/guang19","2196927727@qq.com"),
                "Apache 2.0","http://www.apache.org/licenses/LICENSE-2.0",new ArrayList<>());
    }

}
