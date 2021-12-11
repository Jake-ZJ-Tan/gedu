package com.guli.teacher.controller.front;


import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("讲师前台")
@CrossOrigin
@RestController
@RequestMapping("/front/teacher")
public class FrontTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("前台获取讲师列表")
    @GetMapping("/{page}/{limit}")
    public RespResult listFrontTeacherPage(
            @ApiParam(name = "page", value = "当前页码", defaultValue = "1")
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "8")
            @PathVariable("limit") Long limit
    ) {
        return RespResult.ok().data(teacherService.pageList(page, limit));
    }

    @ApiOperation("根据ID获取讲师详情")
    @GetMapping("/{teacherId}")
    public RespResult getTeacherInfoById(
            @ApiParam(name = "teacherId", value = "讲师ID", required = true)
            @PathVariable("teacherId") String teacherId
    ) {
        return RespResult.ok().data(teacherService.getTeacherInfoById(teacherId));
    }

}
