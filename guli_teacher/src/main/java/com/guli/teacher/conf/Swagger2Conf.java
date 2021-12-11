package com.guli.teacher.conf;

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
    public Docket frontApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-前端模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/front.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    @Bean
    public Docket videoApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-视频模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/video.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }


    @Bean
    public Docket chapterApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-课程章节模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/chapter.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    @Bean
    public Docket courseApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-课程模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/course.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    @Bean
    public Docket subjectApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-科目模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/subject.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    @Bean
    public Docket teacherApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("谷粒学院-讲师模块")
                .apiInfo(webApiInfo())
                .select()
                .paths(PathSelectors.regex("/teacher.*"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .build()
                .enable(true);

    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("谷粒学院-平台系统API文档")
                .description("本文档提供了讲师服务/科目服务/课程服务接口定义")
                .version("1.0")
                .contact(new Contact("Jake", "http://atguigu.com", "jakezj.tan@gmail.com"))
                .build();
    }

}
