package com.guli.vod.conf;

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
    public Docket frontVodApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-视频点播前台模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/front.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    @Bean
    public Docket vodApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-视频点播后台模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/vod.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("谷粒学院-平台系统API文档")
                .description("本文档提供了阿里云视频点播服务接口")
                .version("1.0")
                .contact(new Contact("Jake", "http://atguigu.com", "jakezj.tan@gmail.com"))
                .build();
    }

}
