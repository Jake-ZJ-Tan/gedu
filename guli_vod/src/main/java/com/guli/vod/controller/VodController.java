package com.guli.vod.controller;

import com.guli.common.vo.resp.RespResult;
import com.guli.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api("阿里云点播-上传视频")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/vod")
public class VodController {

    @Autowired
    private VodService vodService;

    @ApiOperation("根据多个videoSourceIds批量删除云端视频")
    @DeleteMapping("/batchRemove")
    public RespResult removeByIds(
            @ApiParam(name = "vidList", value = "视频资源ID列表", required = true)
            @RequestParam("vidList") List<String> vidList
    ) {
        try {
            return vodService.batchRemoveByIds(vidList) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据videoSourceId删除云端视频")
    @DeleteMapping("/{videoSourceId}")
    public RespResult removeById(
            @ApiParam(name = "videoSourceId", value = "视频资源ID", required = true)
            @PathVariable("videoSourceId") String videoSourceId
    ) {
        try {
            return vodService.removeVodByVideoSourceId(videoSourceId) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("上传视频")
    @PostMapping("/upload")
    public RespResult upload(
            @ApiParam(name = "file", value = "视频文件", required = true)
            @RequestParam("file") MultipartFile file
    ) {
        try {
            return RespResult.ok().data("videoSourceId", vodService.uploadVideo(file));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}
