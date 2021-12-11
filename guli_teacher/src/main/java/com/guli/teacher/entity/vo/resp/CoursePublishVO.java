package com.guli.teacher.entity.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(description = "课程发布VO类")
@Data
public class CoursePublishVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程ID")
    private String id; // 课程ID

    @ApiModelProperty(value = "课程名称")
    private String title; // 课程名称

    @ApiModelProperty(value = "一级类目")
    private String subjectParentTitle; // 一级类目

    @ApiModelProperty(value = "二级类目")
    private String subjectTitle; // 二级类目

    @ApiModelProperty(value = "课时")
    private Integer lessonNum; // 课时

    @ApiModelProperty(value = "讲师名称")
    private String teacherName; // 讲师名称

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price; // 课程价格

    @ApiModelProperty(value = "封面")
    private String cover; // 封面

}
