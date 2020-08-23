package com.jk.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.commonutils.JwtUtils;
import com.jk.commonutils.R;
import com.jk.commonutils.ordervo.CourseWebVoOrder;
import com.jk.edu.client.OrderClient;
import com.jk.edu.entity.Course;
import com.jk.edu.entity.chapter.ChapterVo;
import com.jk.edu.entity.frontvo.CourseFrontVo;
import com.jk.edu.entity.frontvo.CourseWebVo;
import com.jk.edu.service.ChapterService;
import com.jk.edu.service.CourseService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Api(description = "前段课程模块")
@RestController
@RequestMapping("/edu/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    /*
    required = false  允许CourseFrontVo为空
     */
    @PostMapping("getFrontCourseList/{current}/{size}")
    public R getCourseFrontList(@PathVariable long current, @PathVariable long size,
                                @RequestBody(required = false)CourseFrontVo courseFrontVo){
        Page<Course> coursePage=new Page<>(current,size);
        Map<String,Object> map=courseService.getFrontCourseList(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    //课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable("courseId")String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息,多表查询
        CourseWebVo courseWebVo=courseService.getBaseCourseInfo(courseId);
        //根据课程id，查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        //根据课程id和用户id查询课程是否已经支付
        boolean buyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList)
                .data("isBuy",buyCourse);
    }

    //根据课程id查询课程信息
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
