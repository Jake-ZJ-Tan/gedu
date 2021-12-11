package com.guli.teacher.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.teacher.entity.vo.req.SubjectCreate;
import com.guli.teacher.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-10-29
 */
@Api("科目管理")
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @ApiOperation("添加科目分类")
    @PostMapping("/save")
    public RespResult save(
            @ApiParam(name = "subjectCreate", value = "科目分类添加对象", required = true)
            @RequestBody SubjectCreate subjectCreate
    ){
        try {
            if (subjectCreate == null)
                return RespResult.fail().message("subjectCreate is NULL");

            if (subjectService.create(subjectCreate))
                return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("根据ID删除科目分类")
    @DeleteMapping("/{id}")
    public RespResult removeById(
            @ApiParam(name = "id", value = "科目分类ID", required = true)
            @PathVariable("id") String id
    ) {
        try {
            if (ObjectUtils.isEmpty(id))
                return RespResult.fail().message("科目分类ID不能为空");
            if (subjectService.removeById(id))
                return RespResult.ok();
            return RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }


    @ApiOperation("获取科目分类树")
    @GetMapping("/tree")
    public RespResult tree() {

        try {
            return RespResult.ok().data("treeList", subjectService.getTree());
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }

    }

    @ApiOperation("导入科目数据")
    @PostMapping("/importData")
    public RespResult importData(
            @ApiParam(name = "file", value = "数据文件", required = true)
            @RequestParam("file") MultipartFile file
    ) {
        try {
            if (ObjectUtils.isEmpty(file))
                return RespResult.fail().message("数据文件不能为空");

            List<String> errorMessageList = subjectService.importExcel(file);

            if (ObjectUtils.isEmpty(errorMessageList))
                return RespResult.ok();
            else
                return RespResult.fail().data("errorMessage", errorMessageList);

        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

