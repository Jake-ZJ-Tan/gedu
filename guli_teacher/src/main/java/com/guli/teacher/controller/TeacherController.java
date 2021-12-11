package com.guli.teacher.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.entity.Teacher;
import com.guli.teacher.entity.vo.req.TeacherCreate;
import com.guli.teacher.entity.vo.req.TeacherModify;
import com.guli.teacher.entity.vo.req.TeacherQuery;
import com.guli.teacher.service.TeacherService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-10-20
 */
@Api("讲师管理")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("根据ID修改讲师")
    @PutMapping("/modifyById")
    public RespResult updateById(@ApiParam(name = "teacherModify", value = "讲师修改请求对象", required = true)
                                 @RequestBody TeacherModify teacherModify) {

        if (ObjectUtils.isEmpty(teacherModify))
            return RespResult.fail();

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherModify, teacher);

        if (teacherService.updateById(teacher))
            return RespResult.ok();
        return RespResult.fail();
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping("/{id}")
    public RespResult getById(@ApiParam(name = "id", value = "讲师ID", required = true)
                              @PathVariable("id") String id) {

        if (ObjectUtils.isEmpty(id))
            return RespResult.fail();

        Teacher teacher = teacherService.getById(id);
        if (ObjectUtils.isEmpty(teacher))
            return RespResult.fail();
        return RespResult.ok().data("result", teacher);
    }

    @ApiOperation("创建讲师")
    @PostMapping("/save")
    public RespResult save(@ApiParam(name = "teacherCreate", value = "创建讲师参数接收对象", required = true)
                           @RequestBody TeacherCreate teacherCreate) {

        log.debug("teacherCreate >>> {}", teacherCreate);

        if (ObjectUtils.isEmpty(teacherCreate))
            return RespResult.fail();

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherCreate, teacher);
        if (teacherService.save(teacher))
            return RespResult.ok();
        return RespResult.fail();
    }

    @ApiOperation("讲师条件列表分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1", dataType = "long", paramType = "path"),
            @ApiImplicitParam(name = "limit", value = "每页记录数", defaultValue = "5", dataType = "long", paramType = "path"),
//            @ApiImplicitParam(name = "teacherQuery", value = "查询条件封装对象")
    })
    @PostMapping("/{current}/{limit}")
    public RespResult listPageByCriteria(@PathVariable(name = "current", required = false) long current,
                                         @PathVariable(name = "limit", required = false) long limit,
                                         @ApiParam(name = "teacherQuery", value = "查询讲师条件接收对象")
                                         @RequestBody(required = false) TeacherQuery teacherQuery) {
        log.debug("current >>> {}", current);
        log.debug("limit >>> {}", limit);
        log.debug("teacherQuery >>> {}", teacherQuery);
        Page<Teacher> page = new Page<>(current, limit);

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(teacherQuery.getName())) {
            queryWrapper.like("name", teacherQuery.getName());
        }
        if (!ObjectUtils.isEmpty(teacherQuery.getLevel())) {
            queryWrapper.eq("level", teacherQuery.getLevel());
        }
        if (!ObjectUtils.isEmpty(teacherQuery.getGmtCreate())) {
            queryWrapper.ge("gmt_create", teacherQuery.getGmtCreate());
        }
        if (!ObjectUtils.isEmpty(teacherQuery.getGmtModified())) {
            queryWrapper.le("gmt_modified", teacherQuery.getGmtModified());
        }
        teacherService.page(page, queryWrapper);

        return RespResult.ok()
                .data("hasPrevious", page.hasPrevious())
                .data("current", page.getCurrent())
                .data("hasNext", page.hasNext())
                .data("size", page.getSize())
                .data("total", page.getTotal())
                .data("rows", page.getRecords());
    }

    @ApiOperation("讲师列表分页")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "current", value = "当前页码", defaultValue = "1", paramType = "path"),
    })
    @GetMapping("/listPage/{current}")
    public RespResult listPage(@PathVariable(name = "current", required = false) long current) {
        log.debug("current >>> {}", current);
        Page<Teacher> page = new Page<>(current, 5);
        teacherService.page(page, null);
        return RespResult.ok()
                .data("hasPrevious", page.hasPrevious())
                .data("current", page.getCurrent())
                .data("hasNext", page.hasNext())
                .data("size", page.getSize())
                .data("total", page.getTotal())
                .data("rows", page.getRecords());
    }

    @ApiOperation("根据ID删除讲师")
    @ApiImplicitParams(value = {
            // 存在获取不到路径参数问题 将paramType设置成path解决
            @ApiImplicitParam(name = "id", value = "讲师ID", required = true, dataType = "String", paramType = "path")
    })
    @DeleteMapping("/{id}")
    public RespResult removeById(
//            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable("id") String id
    ) {
        log.debug("id >>> {}", id);
        boolean flag = teacherService.removeById(id);
        if (flag)
            return RespResult.ok();
        return RespResult.fail();

    }

    @ApiOperation("所有讲师列表")
    @GetMapping("/list")
    public RespResult list() {

//        try {
//            List<Teacher> teacherList = teacherService.list(null);
//            int i = 1 / 0;
//            return RespResult.ok().data("rows", teacherList);
//
//        } catch (Exception e) {
//            throw new EduException(RespCode.FAIL.getCode(), "0不能做除数");
//        }

        return RespResult.ok().data("list", teacherService.list(null));

    }

}

