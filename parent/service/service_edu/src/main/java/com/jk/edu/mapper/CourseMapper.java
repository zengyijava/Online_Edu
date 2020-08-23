package com.jk.edu.mapper;

import com.jk.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jk.edu.entity.frontvo.CourseWebVo;
import com.jk.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
