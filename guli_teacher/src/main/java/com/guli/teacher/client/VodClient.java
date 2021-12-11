package com.guli.teacher.client;

import com.guli.common.vo.resp.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient("GULI-VOD")
public interface VodClient {

    @DeleteMapping("/vod/batchRemove")
    RespResult removeByIds(@RequestParam("vidList") List<String> vidList);

    @DeleteMapping("/vod/{videoSourceId}")
    RespResult removeById(@PathVariable("videoSourceId") String videoSourceId);

}
