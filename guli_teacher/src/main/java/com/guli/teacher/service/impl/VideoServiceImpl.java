package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.teacher.client.VodClient;
import com.guli.teacher.entity.Video;
import com.guli.teacher.mapper.VideoMapper;
import com.guli.teacher.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-11-05
 */
@Transactional(readOnly = true)
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Transactional
    @Override
    public Boolean batchDeleteByCourseId(String courseId) {
        try {
            if (StringUtils.isEmpty(courseId)) throw new EduException(RespCode.FAIL.getCode(), "courseId is NULL");

            /*
                如果有云端视频，则先删除云端视频再删除记录；没有则直接删除记录
             */
            QueryWrapper<Video> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseId);
            List<Video> list = this.list(wrapper);

            if (list != null && list.size() != 0) { // 有小节
                List<String> strList = new ArrayList<>();
                for (Video video : list) {
                    if (StringUtils.isNotEmpty(video.getVideoSourceId())) {
                        strList.add(video.getVideoSourceId());
                    }
                }

                // 删除云端视频
                if (strList.size() != 0 && vodClient.removeByIds(strList).getSuccess()) ; // 有视频并执行删除操作

                // 删除数据库记录
                return this.remove(wrapper);
            }
            return true; // 无小节
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public Boolean deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");

            /*
                如果有云端视频，则先删除云端视频再删除记录；没有则直接删除记录
             */
            Video video = this.getById(id);
            if (video == null) throw new EduException(RespCode.FAIL.getCode(), " No Such Record");
            if (StringUtils.isNotEmpty(video.getVideoSourceId()) && vodClient.removeById(video.getVideoSourceId()).getSuccess()) {
                return this.removeById(id);
            }
            return this.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public Boolean create(Video video) {
        try {
            checkVideoProperties(video);
            return this.save(video);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public Boolean modifyById(Video video) {
        try {
            checkVideoProperties(video);
            if (StringUtils.isEmpty(video.getId())) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");
            return this.updateById(video);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void checkVideoProperties(Video video) {
        if (video == null) throw new EduException(RespCode.FAIL.getCode(), "video is NULL");
        if (StringUtils.isEmpty(video.getCourseId()))
            throw new EduException(RespCode.FAIL.getCode(), "courseId is NULL");
        if (StringUtils.isEmpty(video.getChapterId()))
            throw new EduException(RespCode.FAIL.getCode(), "chapterId is NULL");
        if (StringUtils.isEmpty(video.getTitle())) throw new EduException(RespCode.FAIL.getCode(), "title is NULL");
        if (video.getSort() == null) throw new EduException(RespCode.FAIL.getCode(), "sort is NULL");
    }

    @Override
    public Video retrieveById(String id) {
        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");
            return this.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
