package com.jk.ucenter.service;

import com.jk.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jk.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-07
 */
public interface MemberService extends IService<Member> {

    String login(Member member);

    void register(RegisterVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegister(String day);
}
