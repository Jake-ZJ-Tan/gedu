package com.guli.ucenter.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.ucenter.entity.vo.resp.LoginInfoVo;
import com.guli.ucenter.service.UcenterMemberService;
import com.guli.ucenter.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
@Api("谷粒学院-会员中心管理")
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/ucenter")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 测试
     * zuul网关默认不传递敏感信息
     *
     * @param request
     * @return
     */
    @PostMapping("login")
    public RespResult login(HttpServletRequest request) {
        String token = request.getHeader("token");
        String cookie = request.getHeader("cookie");

        System.out.println(token);
        System.out.println(cookie);

        return RespResult.ok();
    }

    @ApiOperation("根据token获取用户信息")
    @PostMapping("info/{token}")
    public RespResult getInfoByToken(@PathVariable String token) {

        Claims claims = JwtUtils.checkJWT(token);
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");
        String id = (String) claims.get("id");
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setId(id);
        loginInfoVo.setAvatar(avatar);
        loginInfoVo.setNickname(nickname);

        return RespResult.ok().data("userInfo", loginInfoVo);
    }

    @ApiOperation("获取每天的注册人数")
    @GetMapping("/{day}")
    public RespResult countDailyRegister(
            @ApiParam(name = "day", value = "日期", required = true, example = "2018-12-28")
            @PathVariable("day") String day
    ) {
        try {
            return RespResult.ok().data("registerNum", ucenterMemberService.countDailyRegisterNum(day));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

