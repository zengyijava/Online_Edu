package com.jk.edu.service;

import com.jk.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(String courseId);


}
