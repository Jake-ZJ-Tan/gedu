package com.guli.teacher.service;

import com.guli.teacher.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.teacher.entity.vo.req.SubjectCreate;
import com.guli.teacher.entity.vo.resp.Level1Subject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-10-29
 */
public interface SubjectService extends IService<Subject> {

    List<String> importExcel(MultipartFile file);

    List<Level1Subject> getTree();

    Boolean create(SubjectCreate subjectCreate);
}
