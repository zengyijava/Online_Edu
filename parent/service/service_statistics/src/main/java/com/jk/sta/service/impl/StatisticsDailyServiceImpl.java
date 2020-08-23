package com.jk.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.commonutils.R;
import com.jk.sta.client.UcenterClient;
import com.jk.sta.entity.StatisticsDaily;
import com.jk.sta.mapper.StatisticsDailyMapper;
import com.jk.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-25
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    //查询某天注册人数，远程调用
    @Override
    public void registerCount(String day) {
        //向表中添加记录之前先删除日期相同的记录
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        R r = ucenterClient.countRegister(day);
        Integer countRegister =(Integer) r.getData().get("countRegister");

        //将数据加入  统计分析表中
        StatisticsDaily sta=new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day);//统计日期

        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);

    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper=new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        //因为返回有两部分数据：日期 和 日期对应数量
        //前端要求数组json结构，对应后端java代码是list集合
        //创建两个list集合，一个日期list，一个数量list
        List<String> dateList=new ArrayList<>();
        List<Integer> numList=new ArrayList<>();

        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily sta=staList.get(i);
            dateList.add(sta.getDateCalculated());
            switch (type){
                case "login_num":
                    numList.add(sta.getLoginNum());
                    break;
                case "register_num":
                    numList.add(sta.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(sta.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        //把封装之后两个list集合放到map集合，进行返回
        Map<String,Object> map=new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        return map;
    }
}
