package com.jk.ucenter.controller;


import com.jk.commonutils.JwtUtils;
import com.jk.commonutils.R;
import com.jk.commonutils.ordervo.UcenterMemberOrder;
import com.jk.ucenter.entity.Member;
import com.jk.ucenter.entity.vo.RegisterVo;
import com.jk.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-07
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;
    /*
    @RequestBody   必须使用post请求

    Required request body is missing: public com.jk.commonutils.R com.jk.ucenter.controller.MemberController.login(com.jk.ucenter.entity.Member)

     */

    //@GetMapping("login")
    @PostMapping("login")
    public R login(@RequestBody Member member){
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token=memberService.login(member);
        return R.ok().data("token",token);
    }

    @PostMapping("register")
    public R register(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //根据token字符串获取会员id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


    //根据用户id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        Member member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count=memberService.countRegister(day);
        return R.ok().data("countRegister",count);
    }


}

