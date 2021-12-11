package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.Course;
import com.guli.teacher.entity.vo.req.CourseDescriptionVo;
import com.guli.teacher.entity.vo.req.CourseQuery;
import com.guli.teacher.entity.vo.resp.CoursePublishVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
public interface CourseService extends IService<Course> {

    String saveOrUpdateCourse(CourseDescriptionVo vo);

    CourseDescriptionVo getCourseById(String id);

    Page<Course> pageSearch(Long current, Long limit, CourseQuery courseQuery);

    Boolean deleteById(String id);

    CoursePublishVO getCoursePublishVOById(String id);

    Boolean updateStatusById(String id);

    List<Course> listCourseByTeacherId(String teacherId);

    Map<String, Object> pageList(Long page, Long limit);

    Map<String, Object> fetchFrontCourseVOByCourseId(String courseId);

    Boolean updateViewCountById(String id);
}
