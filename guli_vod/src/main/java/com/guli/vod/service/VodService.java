package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    Boolean removeVodByVideoSourceId(String videoSourceId);

    Boolean batchRemoveByIds(List<String> vidList);

    String getVideoPlayAuthByVid(String vid);
}
