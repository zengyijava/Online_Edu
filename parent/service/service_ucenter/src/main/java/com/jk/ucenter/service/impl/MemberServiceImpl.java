package com.jk.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jk.commonutils.JwtUtils;
import com.jk.servicebase.exceptionhandler.MyDefineException;
import com.jk.ucenter.entity.Member;
import com.jk.ucenter.entity.vo.RegisterVo;
import com.jk.ucenter.mapper.MemberMapper;
import com.jk.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.ucenter.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-07
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(Member member) {
        //判断用户名或者密码是否为空
        String mobile=member.getMobile();
        String password=member.getPassword();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new MyDefineException(20001,"手机号码或者密码为空！");
        }

        //判断手机号码是否存在
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Member selectOne = baseMapper.selectOne(wrapper);
        if(selectOne==null){
            throw new MyDefineException(20001,"手机号码不存在！");
        }

        //判断密码是否正确
        if(!MD5.encrypt(password).equals(selectOne.getPassword())){
            throw new MyDefineException(20001,"密码错误！");
        }

        //判断用户是否禁用
        if (selectOne.getIsDisabled()){
            throw new MyDefineException(20001,"用户被禁用！");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(selectOne.getId(), selectOne.getNickname());
        System.out.println(jwtToken+"---------------------------------------------------");
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //非空判断
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String nickname = registerVo.getNickname();
        String code = registerVo.getCode();
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
        || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(code)){
            throw new MyDefineException(20001,"注册信息不能为空！");
        }

        //判断手机号码是否重复，重复不能添加
        QueryWrapper<Member> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count>0){
            throw new MyDefineException(20001,"该手机号码已存在！");
        }

        //判断验证码
        //获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!redisCode.equals(code)){
            throw new MyDefineException(20001,"验证码错误！");
        }

        //数据添加数据库中
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        member.setIsDisabled(false);//用户不禁用
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }

    //根据openid判断
    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Member member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    public Integer countRegister(String day) {

        return baseMapper.countRegister(day);
    }

}
