package com.jk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.edu.entity.Teacher;
import com.jk.edu.mapper.TeacherMapper;
import com.jk.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-03
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage) {
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(teacherPage,wrapper);

        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        long current = teacherPage.getCurrent();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();

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
}
