package com.guli.teacher.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.Teacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-10-20
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> pageList(Long page, Long limit);

    Map<String, Object> getTeacherInfoById(String teacherId);
}
