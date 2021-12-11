package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.teacher.entity.Course;
import com.guli.teacher.entity.CourseDescription;
import com.guli.teacher.entity.vo.req.CourseDescriptionVo;
import com.guli.teacher.entity.vo.req.CourseQuery;
import com.guli.teacher.entity.vo.resp.ChapterVo;
import com.guli.teacher.entity.vo.resp.CoursePublishVO;
import com.guli.teacher.entity.vo.resp.FrontCourseVO;
import com.guli.teacher.mapper.CourseMapper;
import com.guli.teacher.service.ChapterService;
import com.guli.teacher.service.CourseDescriptionService;
import com.guli.teacher.service.CourseService;
import com.guli.teacher.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
@Transactional(readOnly = true)
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Transactional
    @Override
    public Boolean updateViewCountById(String id) {

        Course course = baseMapper.selectById(id);

        course.setViewCount(course.getViewCount() + 1L);
        return baseMapper.updateById(course) == 1;
    }

    @Transactional(noRollbackFor = Exception.class)
    @Override
    public Map<String, Object> fetchFrontCourseVOByCourseId(String courseId) {

        // 更新课程浏览数
        this.updateViewCountById(courseId);

        // 获取课程基本信息
        FrontCourseVO frontCourseVO = baseMapper.retrieveFrontCourseVOByCourseId(courseId);
        // 获取课程大纲信息
        List<ChapterVo> chapterVoList = chapterService.listChapterVo(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("courseFrontInfo", frontCourseVO);
        map.put("chapterVideoList", chapterVoList);
        return map;
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit) {

        Page<Course> page1 = new Page<>(page, limit);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("buy_count");
        baseMapper.selectPage(page1, wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("hasPrevious", page1.hasPrevious());
        map.put("current", page1.getCurrent());
        map.put("total", page1.getTotal());
        map.put("pages", page1.getPages());
        map.put("items", page1.getRecords());
        map.put("hasNext", page1.hasNext());

        return map;
    }

    @Override
    public List<Course> listCourseByTeacherId(String teacherId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId).orderByDesc("gmt_modified");
        return baseMapper.selectList(wrapper);
    }

    @Transactional
    @Override
    public Boolean updateStatusById(String id) {
        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "ID is NULL");
            Course course = new Course();
            course.setId(id);
            course.setStatus("Normal");
            return this.updateById(course);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CoursePublishVO getCoursePublishVOById(String id) {

        try {
            if (StringUtils.isEmpty(id)) throw new EduException(RespCode.FAIL.getCode(), "ID is NULL");
            return baseMapper.getCoursePublishVOById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public Boolean deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                throw new EduException(RespCode.FAIL.getCode(), "ID is NULL");
            }
            return videoService.batchDeleteByCourseId(id) && chapterService.batchRemoveByCourseId(id) && this.removeById(id) && courseDescriptionService.removeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Page<Course> pageSearch(Long current, Long limit, CourseQuery courseQuery) {

        log.debug("current >>> {}", current);
        log.debug("limit >>> {}", limit);
        log.debug("courseQuery >>> {}", courseQuery);

        try {
            if (current == null || current < 1L) current = 1L;
            if (limit == null || limit < 1L) limit = 10L;
            Page<Course> page = new Page<>(current, limit);
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
            if (courseQuery != null) {
                if (StringUtils.isNotEmpty(courseQuery.getTitle()))
                    wrapper.like("title", courseQuery.getTitle().trim());
                if (StringUtils.isNotEmpty(courseQuery.getSubjectParentId()))
                    wrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
                if (StringUtils.isNotEmpty(courseQuery.getSubjectId()))
                    wrapper.eq("subject_id", courseQuery.getSubjectId());
                if (StringUtils.isNotEmpty(courseQuery.getTeacherId()))
                    wrapper.eq("teacher_id", courseQuery.getTeacherId());
            }
            this.page(page, wrapper);
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public CourseDescriptionVo getCourseById(String id) {
        try {
            Course course;
            CourseDescription courseDescription;
            if ((course = this.getById(id)) != null && (courseDescription = courseDescriptionService.getById(id)) != null) {
                CourseDescriptionVo vo = new CourseDescriptionVo();
                vo.setCourse(course);
                vo.setCourseDescription(courseDescription);
                return vo;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public String saveOrUpdateCourse(CourseDescriptionVo vo) {

        log.debug("courseId >>> {}", vo.getCourse().getId());

        try {
            // 保存/更新课程和课程简介
            if (vo.getCourse() == null) throw new EduException(40003, "保存课程失败");
            if (this.saveOrUpdate(vo.getCourse()) && vo.getCourseDescription() != null && courseDescriptionService.saveOrUpdate(vo.getCourseDescription().setId(vo.getCourse().getId()))) {
                return vo.getCourse().getId();
            }
            throw new EduException(40003, "保存课程失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

}
