package com.guli.teacher.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.entity.Video;
import com.guli.teacher.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-11-05
 */
@Api("课程视频管理")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @ApiOperation("根据课程ID批量删除视频")
    @DeleteMapping("/batch/{courseId}")
    public RespResult batchRemoveByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId
    ) {
        try {
            return videoService.batchDeleteByCourseId(courseId) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID删除视频")
    @DeleteMapping("/{id}")
    public RespResult deleteById(
            @ApiParam(name = "id", value = "视频ID", required = true)
            @PathVariable String id
    ) {
        try {
            return videoService.deleteById(id) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("保存视频")
    @PostMapping("/save")
    public RespResult create(
            @ApiParam(name = "video", value = "视频对象", required = true)
            @RequestBody Video video
    ) {
        try {
            return videoService.create(video) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID修改视频")
    @PutMapping("/update")
    public RespResult modifyById(
            @ApiParam(name = "video", value = "视频对象", required = true)
            @RequestBody Video video
    ) {
        try {
            return videoService.modifyById(video) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID获取视频")
    @GetMapping("/{id}")
    public RespResult retrieveById(
            @ApiParam(name = "id", value = "视频ID", required = true)
            @PathVariable("id") String id

    ) {
        try {
            return RespResult.ok().data("record", videoService.retrieveById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

