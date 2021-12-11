package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-11-05
 */
public interface VideoService extends IService<Video> {

    Video retrieveById(String id);

    Boolean modifyById(Video video);

    Boolean create(Video video);

    Boolean deleteById(String id);

    Boolean batchDeleteByCourseId(String courseId);
}
