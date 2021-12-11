package com.guli.teacher.controller;

import com.guli.common.vo.resp.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api("后台登录管理")
@RequestMapping("/user")
@Slf4j
@CrossOrigin
@RestController
public class UserController {

    /**
     * {
     *      "code":20000,
     *      "data":"success"
     * }
     *
     * @return
     */
    @ApiOperation("登出")
    @PostMapping("/logout")
    public RespResult logout() {
        return RespResult
                .ok()
                .data("data", "success");
    }

    /**
     * {
     *      "code":20000,
     *      "data":{
     *              "roles":["admin"],
     *              "introduction":"I am a super administrator",
     *              "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
     *              "name":"Super Admin"
     *       }
     * }
     *
     * @param token
     * @return
     */
    @ApiOperation("获取管理员信息")
    @GetMapping("/info")
    public RespResult info(
            @ApiParam(name = "token", value = "Token", required = true)
            @RequestParam String token
    ) {
        return RespResult
                .ok()
                .data("roles", Arrays.asList("admin").toArray())
                .data("introduction", "I am a super administrator")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .data("name", "Super Admin");
    }

    /**
     * {
     *      "code":20000,
     *      "data":{
     *          "token":"admin-token"
     *      }
     * }
     *
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public RespResult login() {
        return RespResult
                .ok()
                .data("token", "admin-token");
    }

}
