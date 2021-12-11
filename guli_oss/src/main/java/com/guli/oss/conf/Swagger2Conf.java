package com.guli.oss.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootConfiguration
public class Swagger2Conf {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-文件存储服务")
                .apiInfo(webApiInfo())
                .select()
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("谷粒学院-文件存储服务API文档")
                .description("本文档描述了文件存储服务接口定义")
                .version("1.0")
                .contact(new Contact("Jake", "http://atguigu.com", "jakezj.tan@gmail.com"))
                .build();
    }

}
