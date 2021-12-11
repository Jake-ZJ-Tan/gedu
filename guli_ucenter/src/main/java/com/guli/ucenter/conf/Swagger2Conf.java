package com.guli.ucenter.conf;

import io.swagger.annotations.Api;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootConfiguration
public class Swagger2Conf {

    @Bean
    public Docket teacherApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-用户中心模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/ucenter.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("谷粒学院-平台系统API文档")
                .description("本文档提供了用户中心接口定义")
                .version("1.0")
                .contact(new Contact("Jake", "http://atguigu.com", "jakezj.tan@gmail.com"))
                .build();
    }

}
