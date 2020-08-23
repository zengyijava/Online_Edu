package com.jk.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.commonutils.R;
import com.jk.edu.entity.Course;
import com.jk.edu.entity.Teacher;
import com.jk.edu.service.CourseService;
import com.jk.edu.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "前段首页")
@RestController
@RequestMapping("/edu/indexfront")
@CrossOrigin
public class IndexFrontController {

   @Autowired
    private CourseService courseService;
   @Autowired
    private TeacherService teacherService;
    //查询前8条热门课程，查询前4条名师
   @GetMapping("index")
   public R index(){
       //查询前8条热门课程
       QueryWrapper<Course> wrapper=new QueryWrapper<>();
       wrapper.orderByDesc("id");
       wrapper.last("limit 8");
       List<Course> courseList = courseService.list(wrapper);

       //查询前4条名师
       QueryWrapper<Teacher> wrapper1=new QueryWrapper<>();
       wrapper1.orderByDesc("id");
       wrapper1.last("limit 4");
       List<Teacher> teacherList = teacherService.list(wrapper1);

       return R.ok().data("courseList",courseList).data("teacherList",teacherList);
   }

}
