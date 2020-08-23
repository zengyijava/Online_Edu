package com.jk.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-03
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);
}
