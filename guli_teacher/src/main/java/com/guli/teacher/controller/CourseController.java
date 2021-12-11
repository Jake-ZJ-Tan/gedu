package com.guli.teacher.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.entity.Course;
import com.guli.teacher.entity.vo.req.CourseDescriptionVo;
import com.guli.teacher.entity.vo.req.CourseQuery;
import com.guli.teacher.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
@Api("课程管理")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("根据ID更新课程浏览数")
    @PutMapping("/viewCount/{id}")
    public RespResult updateViewCountById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            return courseService.updateViewCountById(id) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID修改课程状态")
    @PutMapping("/{id}")
    public RespResult updateStatusById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            return courseService.updateStatusById(id) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID获取课程发布VO")
    @GetMapping("/vo/{id}")
    public RespResult getVOById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            return RespResult.ok().data("vo", courseService.getCoursePublishVOById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID删除课程")
    @DeleteMapping("/{id}")
    public RespResult deleteById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            if (courseService.deleteById(id))
                return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("获取课程列表")
    @PostMapping("/{current}/{limit}")
    public RespResult pageQuery(
            @ApiParam(name = "current", value = "当前页码", defaultValue = "1")
            @PathVariable(value = "current", required = false) Long current,
            @ApiParam(name = "limit", value = "页面最大显示记录数", defaultValue = "10")
            @PathVariable(value = "limit", required = false) Long limit,
            @ApiParam(name = "courseQuery", value = "查询条件封装对象")
            @RequestBody(required = false) CourseQuery courseQuery
    ) {
        try {
            Page<Course> page = courseService.pageSearch(current, limit, courseQuery);
            return RespResult.ok()
                    .data("hasPrevious", page.hasPrevious())
                    .data("current", page.getCurrent())
                    .data("hasNext", page.hasNext())
                    .data("size", page.getSize())
                    .data("total", page.getTotal())
                    .data("rows", page.getRecords());
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID获取课程和课程简介")
    @GetMapping("/{id}")
    public RespResult getCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            if (StringUtils.isEmpty(id)) return RespResult.fail().message("id is NULL");
            CourseDescriptionVo vo;
            if ((vo = courseService.getCourseById(id)) == null) return RespResult.fail().message("No such record");
            return RespResult.ok().data("vo", vo);
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("保存/更新课程和课程简介")
    @PostMapping("/saveOrUpdate")
    public RespResult saveOrUpdate(
            @ApiParam(name = "vo", value = "封装课程和课程简介的VO对象", required = true)
            @RequestBody CourseDescriptionVo vo
    ) {
        try {
            if (vo == null)
                return RespResult.fail().message("vo is NULL");
            return RespResult.ok().data("courseId", courseService.saveOrUpdateCourse(vo));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

