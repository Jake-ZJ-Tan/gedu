package com.guli.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.ucenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer countDailyRegisterNum(String day);

    UcenterMember getByOpenid(String openid);
}
