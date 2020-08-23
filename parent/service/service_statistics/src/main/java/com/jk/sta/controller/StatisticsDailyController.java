package com.jk.sta.controller;


import com.jk.commonutils.R;
import com.jk.sta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-25
 */
@RestController
@RequestMapping("/edusta/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return R.ok();
    }

    //显示图标
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map=statisticsDailyService.getShowData(type,begin,end);
        return R.ok().data(map);
    }


}

