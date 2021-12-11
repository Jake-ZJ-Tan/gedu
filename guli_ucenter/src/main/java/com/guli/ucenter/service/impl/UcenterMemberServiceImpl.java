package com.guli.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.ucenter.entity.UcenterMember;
import com.guli.ucenter.mapper.UcenterMemberMapper;
import com.guli.ucenter.service.UcenterMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
@Transactional(readOnly = true)
@Slf4j
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public UcenterMember getByOpenid(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    @Override
    public Integer countDailyRegisterNum(String day) {
        try {
            if (StringUtils.isEmpty(day)) {
                throw new EduException(RespCode.FAIL.getCode(), "day is NULL");
            }
            QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
            wrapper.apply("date(gmt_create)={0}", day);
            return this.count(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
