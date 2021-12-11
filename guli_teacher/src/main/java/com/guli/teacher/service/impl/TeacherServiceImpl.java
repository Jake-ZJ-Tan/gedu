package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.teacher.entity.Teacher;
import com.guli.teacher.mapper.TeacherMapper;
import com.guli.teacher.service.CourseService;
import com.guli.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-10-20
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private CourseService courseService;

    @Override
    public Map<String, Object> getTeacherInfoById(String teacherId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherInfo", baseMapper.selectById(teacherId));
        map.put("courseList", courseService.listCourseByTeacherId(teacherId));
        return map;
    }

    @Override
    public Map<String, Object> pageList(Long page, Long limit) {

        Page<Teacher> page1 = new Page<>(page, limit);
        baseMapper.selectPage(page1, null);

        HashMap<String, Object> map = new HashMap<>();
        map.put("hasPrevious", page1.hasPrevious());
        map.put("current", page1.getCurrent());
        map.put("hasNext", page1.hasNext());
        map.put("total", page1.getTotal());
        map.put("pages", page1.getPages());
        map.put("items", page1.getRecords());

        return map;
    }
}
