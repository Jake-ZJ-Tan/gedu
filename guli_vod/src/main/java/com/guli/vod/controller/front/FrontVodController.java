package com.guli.vod.controller.front;

import com.guli.common.vo.resp.RespResult;
import com.guli.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("阿里云视频点播")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/front/vod")
public class FrontVodController {

    @Autowired
    private VodService vodService;

    /**
     * 获取视频播放凭证
     *
     * @return
     */
    @ApiOperation("根据vid获取videoPlayAuth")
    @GetMapping("/{vid}")
    public RespResult getVideoPlayAUthByVid(
            @ApiParam(name = "vid", value = "视频ID", required = true)
            @PathVariable("vid") String vid
    ) {
        return RespResult.ok().data("playAuth", vodService.getVideoPlayAuthByVid(vid));
    }

}
