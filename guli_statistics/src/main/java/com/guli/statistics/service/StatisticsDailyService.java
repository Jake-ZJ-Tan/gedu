package com.guli.statistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.statistics.entity.StatisticsDaily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    Boolean saveDailyRegisterNum(String day);

    Map<String, Object> fetchData(String begin, String end, String type);
}
