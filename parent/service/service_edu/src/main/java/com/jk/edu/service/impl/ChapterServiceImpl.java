package com.jk.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.edu.entity.Chapter;
import com.jk.edu.entity.Video;
import com.jk.edu.entity.chapter.ChapterVo;
import com.jk.edu.entity.chapter.VideoVo;
import com.jk.edu.mapper.ChapterMapper;
import com.jk.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.edu.service.VideoService;
import com.jk.servicebase.exceptionhandler.MyDefineException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1 根据课程id查询课程里面所有的章节
        QueryWrapper<Chapter> wrapperChapter=new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<Chapter> chapterList=baseMapper.selectList(wrapperChapter);

        //2 根据课程id查询课程里面所有的小节
        QueryWrapper<Video> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<Video> videoList=videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList=new ArrayList<>();

        //3 遍历查询章节list集合进行封装
        for (int i = 0; i < chapterList.size(); i++) {
            Chapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            finalList.add(chapterVo);


            //创建集合，用于封装章节的小节
            List<VideoVo> finalVideoList = new ArrayList<>();
            //4 遍历查询小节list集合，进行封装
            for (int j = 0; j < videoList.size(); j++) {
                Video video = videoList.get(j);
                //判断：小节里面chapterid和章节里面id是否一样
                if (video.getChapterId().equals(chapter.getId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    finalVideoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(finalVideoList);
        }

        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        //查询出有几条数据
                int count = videoService.count(wrapper);
        if(count>0){
            throw new MyDefineException(20001,"章节里面有小节，不能删除");
        }else {
            int i = baseMapper.deleteById(chapterId);
            //成功 1>0    失败 0>0
            return i>0;
        }

    }

    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
