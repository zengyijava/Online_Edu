package com.jk.order.controller;


import com.jk.commonutils.R;
import com.jk.order.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //根据订单号生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        Map map=payLogService.createNative(orderNo);
        System.out.println("生成微信支付二维码map："+map);
        return R.ok().data(map);
    }


    //根据订单号查询订单状态
    @GetMapping("queryOrderStatus/{orderNo}")
    public R queryOrderStatus(@PathVariable String orderNo){
        Map<String,String> map=payLogService.queryOrderStatus(orderNo);
        System.out.println("map====================="+map);
        System.out.println("trade_state============="+map.get("trade_state"));
        if(map==null){
            return R.error().message("查询订单号失败");
        }
        //如果状态不为空，则修改状态
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updatePayStauts(map);
            return R.ok().message("支付成功！！！");
        }
        return R.ok().code(25000).message("支付中...");
    }


}

