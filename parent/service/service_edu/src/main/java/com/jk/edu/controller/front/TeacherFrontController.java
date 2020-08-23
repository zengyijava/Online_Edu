package com.jk.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.commonutils.R;
import com.jk.edu.entity.Course;
import com.jk.edu.entity.Teacher;
import com.jk.edu.service.CourseService;
import com.jk.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前段讲师模块")
@RestController
@RequestMapping("/edu/teacherfront")
@CrossOrigin
public class TeacherFrontController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "获取讲师分页列表")
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontPage(@PathVariable long page,@PathVariable long limit){
        Page<Teacher> teacherPage=new Page<>(page,limit);
        Map<String,Object> map=teacherService.getTeacherFrontList(teacherPage);
        //返回分页所以数据
        return R.ok().data(map);
    }

    @GetMapping("getTeacherInfoById/{teacherId}")
    public R getTeacherInfoById(@PathVariable String teacherId){
        //根据讲师id查询讲师信息
        Teacher teacher = teacherService.getById(teacherId);
        //根据讲师id查询所讲课程
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<Course> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }



}
