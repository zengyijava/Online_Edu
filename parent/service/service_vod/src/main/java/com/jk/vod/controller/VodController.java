package com.jk.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.jk.commonutils.R;
import com.jk.servicebase.exceptionhandler.MyDefineException;
import com.jk.vod.service.VodService;
import com.jk.vod.utils.ConstantVodUtil;
import com.jk.vod.utils.InitVodCilent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description = "阿里云上传视频")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation(value = "返回上传视频id值")
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file){
        //返回上传视频id值
        String videoId=vodService.uploadVideoAly(file);
        return R.ok().data("videoId",videoId);

    }

    //根据视频id删除阿里云视频
    @ApiOperation(value = "根据视频id删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e) {
            e.printStackTrace();
            throw new MyDefineException(20001,"删除视频失败");
        }
    }

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @ApiOperation(value = "删除多个阿里云视频的方法")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }


    //根据视频id获取视频凭证
    @ApiOperation(value = "根据视频id获取视频凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            System.out.println("视频id："+id);
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtil.ACCESS_KEY_ID, ConstantVodUtil.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            System.out.println("视频凭证："+playAuth);
            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {

            throw new MyDefineException(20001,"获取凭证失败");
        }
    }



}
