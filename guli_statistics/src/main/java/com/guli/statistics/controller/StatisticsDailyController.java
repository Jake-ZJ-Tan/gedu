package com.guli.statistics.controller;


import com.guli.common.vo.resp.RespResult;
import com.guli.statistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
@Api("谷粒学院-日常数据统计分析")
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/statistics")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("获取图表数据")
    @GetMapping("/{begin}/{end}/{type}")
    public RespResult getDataCharts(
            @ApiParam(name = "begin", value = "开始日期", required = true, example = "2018-12-28")
            @PathVariable("begin") String begin,
            @ApiParam(name = "end", value = "截止日期", required = true, example = "2018-12-30")
            @PathVariable("end") String end,
            @ApiParam(name = "type", value = "统计因子", required = true)
            @PathVariable("type") String type
    ) {
        try {
            return RespResult.ok().data(statisticsDailyService.fetchData(begin, end, type));
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

    @ApiOperation("保存每日注册人数")
    @PostMapping("/saveRegisterNum/{day}")
    public RespResult saveDailyRegister(
            @ApiParam(name = "day", value = "日期", required = true, example = "2018-12-28")
            @PathVariable("day") String day
    ) {
        try {
            return statisticsDailyService.saveDailyRegisterNum(day) ? RespResult.ok() : RespResult.fail();
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.fail().message(e.getMessage());
        }
    }

}

