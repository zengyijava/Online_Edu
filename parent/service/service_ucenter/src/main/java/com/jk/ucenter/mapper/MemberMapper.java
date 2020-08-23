package com.jk.ucenter.mapper;

import com.jk.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author zy
 * @since 2020-06-07
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegister(String day);
}
