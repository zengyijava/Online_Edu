package com.jk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.edu.entity.Course;
import com.jk.edu.entity.CourseDescription;
import com.jk.edu.entity.frontvo.CourseFrontVo;
import com.jk.edu.entity.frontvo.CourseWebVo;
import com.jk.edu.entity.vo.CourseInfoVo;
import com.jk.edu.entity.vo.CoursePublishVo;
import com.jk.edu.mapper.CourseMapper;
import com.jk.edu.service.ChapterService;
import com.jk.edu.service.CourseDescriptionService;
import com.jk.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.edu.service.VideoService;
import com.jk.servicebase.exceptionhandler.MyDefineException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    //注入小节和章节service
    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int insert=baseMapper.insert(course);
        if(insert==0){
            throw new MyDefineException(20001,"课程添加失败！！！");
        }
        String cid=course.getId();
        //添加课程描述
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        courseDescription.setId(cid);   //使课程表和描述表 添加时id相同

        courseDescriptionService.save(courseDescription);
        return cid;

    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        Course course=baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo=new CourseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);

        CourseDescription courseDescription=courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        Course course=new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int update=baseMapper.updateById(course);
        if(update== 0){
            throw new MyDefineException(20001,"修改课程表失败！！！");
        }
        //修改描述表
        CourseDescription courseDescription=new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseInfoVo.getId());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        videoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        courseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) { //失败返回
            throw new MyDefineException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<Course> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){   //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){   //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){   //关注度
            wrapper.orderByDesc("buy_count",courseFrontVo.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){   //最新
            wrapper.orderByDesc("gmt_create",courseFrontVo.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){   //价格
            wrapper.orderByDesc("price",courseFrontVo.getPriceSort());
        }
        baseMapper.selectPage(coursePage,wrapper);

        List<Course> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        long current = coursePage.getCurrent();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();

        Map<String,Object> map=new HashMap<>();
        map.put("records",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {

        return baseMapper.getBaseCourseInfo(courseId);
    }


}
