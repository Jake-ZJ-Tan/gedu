package com.guli.teacher.controller.front;


import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("课程前台")
@CrossOrigin
@RestController
@RequestMapping("/front/course")
public class FrontCourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("根据课程ID获取课程详情")
    @GetMapping("/detail/{courseId}")
    public RespResult getFrontCourseVOByCourseId(
            @ApiParam(name = "courseId", value = "课程ID",required = true)
            @PathVariable("courseId") String courseId
    ) {
        return RespResult.ok().data(courseService.fetchFrontCourseVOByCourseId(courseId));
    }

    @ApiOperation("前台获取课程列表")
    @GetMapping("/{page}/{limit}")
    public RespResult listFrontCoursePage(
            @ApiParam(name = "page", value = "当前页码", defaultValue = "1")
            @PathVariable("page") Long page,
            @ApiParam(name = "limit", value = "每页记录数", defaultValue = "8")
            @PathVariable("limit") Long limit
    ) {
        return RespResult.ok().data(courseService.pageList(page, limit));
    }

    @ApiOperation("根据讲师ID获取课程列表")
    @GetMapping("/{teacherId}")
    public RespResult getCourseListByTeacherId(
            @ApiParam(name = "teacherId", value = "讲师ID", required = true)
            @PathVariable("teacherId") String teacherId
    ) {
        return RespResult.ok().data("courseList", courseService.listCourseByTeacherId(teacherId));
    }
}
