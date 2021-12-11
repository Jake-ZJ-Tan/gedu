package com.guli.teacher.entity.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "讲师查询对象", description = "封装讲师查询参数")
@Data
public class TeacherQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @ApiModelProperty(value = "讲师头衔 1--高级讲师 2--首席讲师")
    private Integer level;

    @ApiModelProperty(value = "创建时间", example = "2021-10-20 21:48:54")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间", example = "2021-10-20 21:48:54")
    private Date gmtModified;

}
