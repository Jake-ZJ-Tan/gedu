package com.guli.teacher.mapper;

import com.guli.teacher.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.teacher.entity.vo.resp.CoursePublishVO;
import com.guli.teacher.entity.vo.resp.FrontCourseVO;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Jake
 * @since 2021-11-01
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVO getCoursePublishVOById(String id);

    FrontCourseVO retrieveFrontCourseVOByCourseId(String courseId);
}
