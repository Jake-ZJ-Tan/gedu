package com.guli.statistics.client;

import com.guli.common.vo.resp.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-ucenter")
@Component
public interface UcenterClient {

    @GetMapping("/ucenter/{day}")
    RespResult countDailyRegister(@PathVariable("day") String day);

}
