package com.guli.teacher.entity.vo.req;

import com.guli.teacher.entity.Course;
import com.guli.teacher.entity.CourseDescription;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "CourseDescriptionVo对象", description = "封装课程和课程简介的VO类")
@Data
public class CourseDescriptionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "课程对象", required = true)
    private Course course;

    @ApiModelProperty(value = "课程简介对象", required = true)
    private CourseDescription courseDescription;

}
