package com.jk.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.commonutils.JwtUtils;
import com.jk.commonutils.R;
import com.jk.order.entity.Order;
import com.jk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    //生成订单号
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo=orderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("order_no",orderId);   //订单号
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    //根据课程id和用户id查询订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId) ;
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

}

