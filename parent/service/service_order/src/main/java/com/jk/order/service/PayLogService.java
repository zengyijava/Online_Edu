package com.jk.order.service;

import com.jk.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);


    Map<String, String> queryOrderStatus(String orderNo);

    void updatePayStauts(Map<String, String> map);
}
