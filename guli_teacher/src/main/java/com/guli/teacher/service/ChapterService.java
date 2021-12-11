package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.Chapter;
import com.guli.teacher.entity.vo.resp.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> listChapterVo(String courseId);

    Boolean create(Chapter chapter);

    Boolean modifyById(Chapter chapter);

    Boolean deleteById(String id);

    Chapter retrieveById(String id);

    Boolean batchRemoveByCourseId(String courseId);
}
