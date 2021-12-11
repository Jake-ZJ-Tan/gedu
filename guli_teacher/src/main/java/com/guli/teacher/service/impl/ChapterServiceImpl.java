package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.teacher.entity.Chapter;
import com.guli.teacher.entity.Video;
import com.guli.teacher.entity.vo.resp.ChapterVo;
import com.guli.teacher.entity.vo.resp.VideoVo;
import com.guli.teacher.mapper.ChapterMapper;
import com.guli.teacher.service.ChapterService;
import com.guli.teacher.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public Boolean batchRemoveByCourseId(String courseId) {
        try {
            if (StringUtils.isEmpty(courseId)) throw new EduException(RespCode.FAIL.getCode(), "courseId is NULL");
            QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseId);
            return this.remove(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Chapter retrieveById(String id) {
        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");
            return this.getById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");
            QueryWrapper<Video> wrapper = new QueryWrapper<>();
            wrapper.eq("chapter_id", id);
            List<Video> videoList;
            if ((videoList = videoService.list(wrapper)) != null && videoList.size() != 0) { // 章节下有小节视频
                throw new EduException(RespCode.FAIL.getCode(), "该章节下存在小节视频，不允许删除");
            }
            return this.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean modifyById(Chapter chapter) {
        try {
            checkChapterProperties(chapter);
            if (StringUtils.isEmpty(chapter.getId())) throw new EduException(RespCode.FAIL.getCode(), "id is NULL");
            return this.updateById(chapter);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean create(Chapter chapter) {
        try {
            checkChapterProperties(chapter);
            return this.save(chapter);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<ChapterVo> listChapterVo(String courseId) {
        try {
            if (StringUtils.isEmpty(courseId)) throw new EduException(RespCode.FAIL.getCode(), "courseId is NULL");
            QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseId).orderByAsc("sort", "id");
            List<Chapter> chapterList;
            if (ObjectUtils.isEmpty((chapterList = this.list(wrapper)))) return null;
            List<ChapterVo> chapterVoList = new ArrayList<>();
            ChapterVo chapterVo;
            QueryWrapper<Video> queryWrapper;
            List<Video> videoList;
            VideoVo videoVo;
            for (Chapter chapter : chapterList) {
                chapterVo = new ChapterVo();
                chapterVo.setId(chapter.getId());
                chapterVo.setTitle(chapter.getTitle());
                // 根据章节ID查询小节列表
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("chapter_id", chapterVo.getId()).orderByAsc("sort", "id");
                if (ObjectUtils.isEmpty((videoList = videoService.list(queryWrapper)))) {
                    chapterVoList.add(chapterVo);
                    continue;
                }
                for (Video video : videoList) {
                    videoVo = new VideoVo();
                    videoVo.setId(video.getId());
                    videoVo.setTitle(video.getTitle());
                    videoVo.setVideoSourceId(video.getVideoSourceId());
                    videoVo.setIsFree(video.getIsFree());// 是否免费
                    chapterVo.getChildren().add(videoVo);
                }
                chapterVoList.add(chapterVo);
            }
            return chapterVoList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void checkChapterProperties(Chapter chapter) {
        if (chapter == null) throw new EduException(RespCode.FAIL.getCode(), "chapter is NULL");
        if (StringUtils.isEmpty(chapter.getCourseId()))
            throw new EduException(RespCode.FAIL.getCode(), "courseId is NULL");
        if (StringUtils.isEmpty(chapter.getTitle()))
            throw new EduException(RespCode.FAIL.getCode(), "title is NULL");
        if (chapter.getSort() == null) throw new EduException(RespCode.FAIL.getCode(), "sort is NULL");
    }

}
