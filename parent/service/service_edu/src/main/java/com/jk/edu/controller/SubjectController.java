package com.jk.edu.controller;


import com.jk.commonutils.R;
import com.jk.edu.entity.subject.OneSubject;
import com.jk.edu.service.SubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-09
 */
@RestController
@RequestMapping("/edu/subject")
@CrossOrigin
@Api(description = "分类管理")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    @GetMapping("getAllSubject")
    public R getAllSubject(){
    //list集合泛型是一级分类
        List<OneSubject> list=subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

