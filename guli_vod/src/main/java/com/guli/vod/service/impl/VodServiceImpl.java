package com.guli.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.vod.conf.ConstantPropertiesConf;
import com.guli.vod.service.VodService;
import com.guli.vod.util.AliyunVodSdkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String getVideoPlayAuthByVid(String vid) {
        try {
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(ConstantPropertiesConf.ACCESS_KEY_ID, ConstantPropertiesConf.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(vid);
            request.setSysReadTimeout(1000);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(RespCode.FAIL.getCode(), e.getLocalizedMessage());
        }
    }

    @Override
    public Boolean batchRemoveByIds(List<String> vidList) {

        try {
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(ConstantPropertiesConf.ACCESS_KEY_ID, ConstantPropertiesConf.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            DeleteVideoResponse response = new DeleteVideoResponse();
            String str = org.apache.commons.lang3.StringUtils.join(vidList.toArray(), ",");

            //????????????????????????ID????????????????????????
            request.setVideoIds(str);
            request.setSysReadTimeout(1000);
            response = client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new EduException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    @Override
    public Boolean removeVodByVideoSourceId(String videoSourceId) {
        DeleteVideoResponse response = new DeleteVideoResponse();
        try {
            DefaultAcsClient client = AliyunVodSdkUtil.initVodClient(ConstantPropertiesConf.ACCESS_KEY_ID, ConstantPropertiesConf.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //????????????????????????ID????????????????????????
            request.setVideoIds(videoSourceId);
            response = client.getAcsResponse(request);
            return true;
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
            throw new EduException(RespCode.FAIL.getCode(), e.getMessage());
        }
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesConf.ACCESS_KEY_ID,
                    ConstantPropertiesConf.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //??????????????????URL?????????????????????????????????????????????VideoId???????????????????????????
            // ??????????????????????????????VideoId??????????????????????????????????????????????????????????????????
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "????????????????????????" + "code???" + response.getCode() + ", message???" + response.getMessage();
                log.warn(errorMessage);
                if (StringUtils.isEmpty(videoId)) {
                    throw new EduException(20001, errorMessage);
                }
            }
            return videoId;
        } catch (IOException e) {
            throw new EduException(20001, "guli vod ??????????????????");
        }

    }
}
