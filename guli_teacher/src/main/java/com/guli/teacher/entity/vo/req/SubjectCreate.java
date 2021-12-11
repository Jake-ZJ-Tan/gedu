package com.guli.teacher.entity.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "SubjectCreate对象", description = "封装课程分类添加请求参数")
@Data
public class SubjectCreate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别名称")
    private String title;

    @ApiModelProperty(value = "父ID")
    private String parentId = "0"; // 0表示一级分类

}
