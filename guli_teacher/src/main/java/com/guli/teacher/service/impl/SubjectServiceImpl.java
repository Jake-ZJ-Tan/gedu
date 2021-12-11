package com.guli.teacher.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IOUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.exception.EduException;
import com.guli.teacher.entity.Subject;
import com.guli.teacher.entity.vo.req.SubjectCreate;
import com.guli.teacher.entity.vo.resp.Level1Subject;
import com.guli.teacher.entity.vo.resp.Level2Subject;
import com.guli.teacher.mapper.SubjectMapper;
import com.guli.teacher.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-10-29
 */
@Slf4j
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public Boolean create(SubjectCreate subjectCreate) {

        try {
            // 判断该课程分类是否存在
            QueryWrapper<Subject> wrapper = new QueryWrapper<>();
            if(!ObjectUtils.isEmpty(subjectCreate.getTitle()))
                wrapper.eq("title", subjectCreate.getTitle()).eq("parent_id", subjectCreate.getParentId());
            if (this.getOne(wrapper) != null) { // 存在
                return false;
            }
            Subject subject = new Subject();
            BeanUtils.copyProperties(subjectCreate, subject);
            return this.save(subject);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }

    @Override
    public List<Level1Subject> getTree() {
        try {
            ArrayList<Level1Subject> level1SubjectList = new ArrayList<>();
            // 获取一级课程分类
            QueryWrapper<Subject> wrapper = new QueryWrapper<>();
            wrapper.eq("parent_id", "0");
            List<Subject> subject1List = this.list(wrapper);
            if(ObjectUtils.isEmpty(subject1List))
                return null; // 没有一级分类
            Level1Subject level1Subject;
            String parentId;
            Level2Subject level2Subject;
            for (Subject subject1 : subject1List) {
                level1Subject = new Level1Subject();
                BeanUtils.copyProperties(subject1, level1Subject);
                parentId = subject1.getId(); // 一级分类的ID作为父ID
                // 根据parentId获取二级分类列表
                wrapper = new QueryWrapper<>();
                wrapper.eq("parent_id", parentId);
                List<Subject> subject2List = this.list(wrapper);
                if (ObjectUtils.isEmpty(subject2List)){
                    level1SubjectList.add(level1Subject);
                    continue;
                }
                for (Subject subject2 : subject2List) {
                    level2Subject = new Level2Subject();
                    BeanUtils.copyProperties(subject2, level2Subject);
                    level1Subject.getChildren().add(level2Subject);
                }
                level1SubjectList.add(level1Subject);
            }
            return level1SubjectList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> importExcel(MultipartFile file) {

        List<String> errorMessageList = new ArrayList<>();
        InputStream is = null;
        try {
            Workbook workbook = new HSSFWorkbook((is = file.getInputStream()));
            Sheet sheet = workbook.getSheetAt(0); // 获取第一张工作表
            int lastRowNum = sheet.getLastRowNum(); // 获取最后一行的行号 从0开始
            log.debug("lastRowNum >>> {}", lastRowNum);
            if (lastRowNum == 0) { // Excel 没有数据
                errorMessageList.add("empty data");
                return errorMessageList;
            }
            for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) { // 逐行获取数据
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0); // 获取第1列的数据
                String cellValue;
                if (cell == null || ObjectUtils.isEmpty(cellValue = cell.getStringCellValue())) { // 判断单元格是否为空
                    errorMessageList.add("（" + rowNum + ", 0）data undefined");
                    continue;
                }
                // 查看数据库是否有相同的一级分类课程
                QueryWrapper<Subject> wrapper = new QueryWrapper<>();
                wrapper.eq("title", cellValue).eq("parent_id", "0");
                Subject subject = this.getOne(wrapper);
                String parentId;
                if (subject == null) { // 没有
                    // 插入数据
                    subject = new Subject();
                    subject.setTitle(cellValue);
                    this.save(subject);
                    parentId = subject.getId();
                } else { // 有
                    parentId = subject.getId();
                }
                // 获取第2列的数据
                cell = row.getCell(1);
                if (cell == null || ObjectUtils.isEmpty(cellValue = cell.getStringCellValue())) { // 判断单元格是否为空
                    errorMessageList.add("（" + rowNum + ", 1）data undefined");
                    continue;
                }
                // 查看数据库是否有相同的二级分类课程
                wrapper = new QueryWrapper<>();
                wrapper.eq("title", cellValue).eq("parent_id", parentId);
                subject = this.getOne(wrapper);
                if (subject == null) { // 没有
                    // 插入数据
                    subject = new Subject();
                    subject.setTitle(cellValue);
                    subject.setParentId(parentId);
                    this.save(subject);
                }
            }
            return errorMessageList;
        } catch (Exception e) {
            e.printStackTrace();
            errorMessageList.add(e.getMessage());
            return errorMessageList;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

}
