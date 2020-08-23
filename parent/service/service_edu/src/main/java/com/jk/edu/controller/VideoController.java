package com.jk.edu.controller;



import com.jk.commonutils.R;
import com.jk.edu.client.VodClient;
import com.jk.edu.entity.Video;
import com.jk.edu.service.VideoService;
import com.jk.servicebase.exceptionhandler.MyDefineException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@Api(description = "章节管理")
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;
    //nacos注册中心，远程调用删除视频方法
    @Autowired
    private VodClient vodClient;

    //添加章节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }

    //删除小节的同时删除视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id查询视频id
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)){
            //通过视频id删除视频
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new MyDefineException(20001,"删除视频失败,熔断器...");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }



}

