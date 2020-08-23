package com.jk.order.service.impl;

import com.jk.commonutils.ordervo.CourseWebVoOrder;
import com.jk.commonutils.ordervo.UcenterMemberOrder;
import com.jk.order.client.EduClient;
import com.jk.order.client.UcenterClient;
import com.jk.order.entity.Order;
import com.jk.order.mapper.OrderMapper;
import com.jk.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.order.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eudClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String saveOrder(String courseId, String memberId) {
        //根据课程id查询课程信息，sql语句多表查询
        CourseWebVoOrder courseInfoOrder = eudClient.getCourseInfoOrder(courseId);
        //根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());//订单号
        order.setCourseId(courseId); //课程id
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(1);  //支付类型 ，微信1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
}
