package com.jk.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jk.edu.entity.frontvo.CourseFrontVo;
import com.jk.edu.entity.frontvo.CourseWebVo;
import com.jk.edu.entity.vo.CourseInfoVo;
import com.jk.edu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
public interface CourseService extends IService<Course> {

    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);


    Map<String, Object> getFrontCourseList(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
