package com.guli.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.enumeration.resp.RespCode;
import com.guli.common.exception.EduException;
import com.guli.statistics.client.UcenterClient;
import com.guli.statistics.entity.StatisticsDaily;
import com.guli.statistics.mapper.StatisticsDailyMapper;
import com.guli.statistics.service.StatisticsDailyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Jake
 * @since 2021-11-10
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public Map<String, Object> fetchData(String begin, String end, String type) {
        try {
            if (StringUtils.isEmpty(begin)) {
                throw new EduException(RespCode.FAIL.getCode(), "begin is NULL");
            }
            if (StringUtils.isEmpty(end)) {
                throw new EduException(RespCode.FAIL.getCode(), "end is NULL");
            }
            if (StringUtils.isEmpty(type)) {
                throw new EduException(RespCode.FAIL.getCode(), "type is NULL");
            }
            QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
            wrapper.between("date_calculated", begin, end).select("date_calculated", type);
            List<StatisticsDaily> list = baseMapper.selectList(wrapper);
            if (list != null && list.size() != 0) { // 有数据
                List<String> dateList = new ArrayList<>(); // 存放统计日期
                List<Integer> numList = new ArrayList<>(); // 存放因子数据
                for (StatisticsDaily daily : list) {
                    dateList.add(daily.getDateCalculated());
                    if ("register_num".equals(type)) {
                        numList.add(daily.getRegisterNum());
                        continue;
                    }
                    if ("login_num".equals(type)) {
                        numList.add(daily.getLoginNum());
                        continue;
                    }
                    if ("video_view_num".equals(type)) {
                        numList.add(daily.getVideoViewNum());
                        continue;
                    }
                    if ("course_num".equals(type)) {
                        numList.add(daily.getCourseNum());
                        continue;
                    }
                }
                Map<String, Object> map = new HashMap<>();
                map.put("dateList", dateList);
                map.put("numList", numList);
                return map;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    @Override
    public Boolean saveDailyRegisterNum(String day) {
        try {
            if (StringUtils.isEmpty(day)) {
                throw new EduException(RespCode.FAIL.getCode(), "day is NULL");
            }
            // 远程调用
            Integer registerNum = (Integer) ucenterClient.countDailyRegister(day).getData().get("registerNum");
            // 添加之前先判断表中是否存在该日期记录，存在则修改，反之添加
            QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
            wrapper.eq("date_calculated", day);
            StatisticsDaily one = this.getOne(wrapper);
            if (one != null) { // 存在
                // 修改
                one.setRegisterNum(registerNum);
                return this.updateById(one);
            }
            // 不存在 添加
            one = new StatisticsDaily();
            one.setDateCalculated(day).setRegisterNum(registerNum);
            return this.save(one);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
