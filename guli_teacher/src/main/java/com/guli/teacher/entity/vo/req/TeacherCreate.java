package com.guli.teacher.entity.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "讲师创建对象", description = "封装讲师创建参数")
@Data
public class TeacherCreate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师姓名", required = true)
    private String name;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师", required = true)
    private String intro;

    @ApiModelProperty(value = "讲师简介")
    private String career;

    @ApiModelProperty(value = "讲师头衔 1高级讲师 2首席讲师", required = true)
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

}
