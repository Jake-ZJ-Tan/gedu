package com.guli.oss.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.oss.conf.OssConf;
import com.guli.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

@Api("文件存储服务管理")
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("oss")
public class OssController {

    @Autowired
    OssService ossService;


    @ApiOperation("文件上传")
    @PostMapping("fileUpload")
    public RespResult fileUpload(
            @ApiParam(name = "file", value = "上传文件")
            @RequestParam("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件上传目录")
            @RequestParam(value = "host", required = false) String host
    ) {

        log.debug("file >>> {}", file);
        log.debug("host >>> {}", host);

        try {
            if (ObjectUtils.isEmpty(file))
                return RespResult.fail().message("上传文件不能为空");

            if (!StringUtils.isEmpty(host)) {
                OssConf.FILE_HOST = host;
            }
            final String[] TYPE = {".bmp", ".jpg", ".jpeg", ".png", ".gif"};

            boolean flag = false;
            // 判断文件格式
            for (String type : TYPE) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                return RespResult.fail().message("文件格式不支持");

            // 判断文件内容
            if (ImageIO.read(file.getInputStream()) == null)
                return RespResult.fail().message("文件内容非法");

            return RespResult.ok().data("url", ossService.fileUpload(file));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }

    }

}
