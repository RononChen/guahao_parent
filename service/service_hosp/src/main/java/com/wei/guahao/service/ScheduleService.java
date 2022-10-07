package com.wei.guahao.service;

import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface ScheduleService {
//    上传科室
    void saveSchedule(Map<String, Object> paramMap);

      Page<Schedule> findSchedule(int page, int limit,
                                         ScheduleQueryVo scheduleQueryVo);

    void removeSchedule(String hoscode, String hosScheduleId);
}
