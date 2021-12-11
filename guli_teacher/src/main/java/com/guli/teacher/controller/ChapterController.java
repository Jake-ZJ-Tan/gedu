package com.guli.teacher.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.entity.Chapter;
import com.guli.teacher.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
@Api("课程章节管理")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("根据课程ID批量删除章节")
    @DeleteMapping("/batch/{courseId}")
    public RespResult batchRemoveByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable("courseId") String courseId
    ) {
        try {
            return chapterService.batchRemoveByCourseId(courseId) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID获取章节")
    @GetMapping("/{id}")
    public RespResult retrieveById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            return RespResult.ok().data("record", chapterService.retrieveById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID删除章节")
    @DeleteMapping("/{id}")
    public RespResult deleteById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            if (chapterService.deleteById(id)) return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID修改章节")
    @PutMapping("/update")
    public RespResult updateById(
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter
    ) {
        try {
            if (chapterService.modifyById(chapter)) return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("保存章节")
    @PostMapping("/save")
    public RespResult save(
            @ApiParam(name = "chapter", value = "章节对象", required = true)
            @RequestBody Chapter chapter
    ) {
        try {
            if (chapterService.create(chapter)) return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据课程ID获取章节列表")
    @GetMapping("/list/{courseId}")
    public RespResult listChapterVo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable("courseId") String courseId
    ) {
        try {
            return RespResult.ok().data("chapterList", chapterService.listChapterVo(courseId));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

