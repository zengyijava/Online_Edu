package com.jk.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.commonutils.R;
import com.jk.edu.entity.Course;
import com.jk.edu.entity.vo.CourseInfoVo;
import com.jk.edu.entity.vo.CoursePublishVo;
import com.jk.edu.entity.vo.CourseQuery;
import com.jk.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
@Api(description = "课程管理")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public R getCourseList() {
        List<Course> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String cid=courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",cid);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        Course course=new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

    //课程分页查询
    @PostMapping("findByPageCondition/{current}/{size}")
    public R FindByPageCondition(@PathVariable Long current, @PathVariable Long size,
                                 @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> pageCourse=new Page<>(current,size);
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        String title=courseQuery.getTitle();
        String status=courseQuery.getStatus();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        courseService.page(pageCourse,wrapper);
        List<Course> records = pageCourse.getRecords();
        long total = pageCourse.getTotal();
        return R.ok().data("records",records).data("total",total);
    }





}

