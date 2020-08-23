package com.jk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.edu.client.VodClient;
import com.jk.edu.entity.Video;
import com.jk.edu.mapper.VideoMapper;
import com.jk.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<Video> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        //将video_source_id字段查询出来
        wrapperVideo.select("video_source_id");
        List<Video> videoList = baseMapper.selectList(wrapperVideo);

        List<String> vsIds=new ArrayList<>();
        for (int i = 0; i < videoList.size(); i++) {
            Video video=videoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                vsIds.add(videoSourceId);
            }
        }

        //根据视频id删除视频
        if(vsIds.size()>0){
            vodClient.deleteBatch(vsIds);
        }

        QueryWrapper<Video> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
