package com.jk.sta.service;

import com.jk.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-25
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}
