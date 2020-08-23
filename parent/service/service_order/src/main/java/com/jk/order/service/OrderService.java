package com.jk.order.service;

import com.jk.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
public interface OrderService extends IService<Order> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
