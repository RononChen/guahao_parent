package com.wei.guahao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.model.hosp.Schedule;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.atguigu.yygh.vo.hosp.ScheduleQueryVo;
import com.wei.guahao.repository.ScheduleRepository;
import com.wei.guahao.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;



    @Override
    public void removeSchedule(String hoscode, String hosScheduleId) {
        //        首先是根据医院编码和科室编码查询下有没有科室信息 有才能删除
        Schedule schedule =
                scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);

        if (schedule != null){
            scheduleRepository.deleteById(schedule.getId());
        }
    }





//    分页查询排班信息
    @Override
    public Page<Schedule> findSchedule(int page, int limit,
                                         ScheduleQueryVo scheduleQueryVo) {
//        分页 模糊查询
//        分页
        Pageable pageable = PageRequest.of(page, limit);
//        查询的条件
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
//        模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
//        封装成一个Example
        Example<Schedule> example = Example.of(schedule, exampleMatcher);
        Page<Schedule> pageSchedule = scheduleRepository.findAll(example, pageable);

        return pageSchedule;
    }



    //    上传排班
    @Override
    public void saveSchedule(Map<String, Object> paramMap) {

//       1 保存科室 实体类是Department 想法将map转成Department
        String mapStr = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(mapStr, Schedule.class);


//        2 通过医院编号和排班编号查询是否有排班数据  有就更新 没有就保存
        Schedule scheduleExist =
                scheduleRepository.getScheduleByHoscodeAndHosScheduleId(
                        schedule.getHoscode(),schedule.getHosScheduleId());

//        3 有数据 更新操作
        if (scheduleExist != null){
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleRepository.save(scheduleExist);

        }else {
//            没有数据保存操作
            schedule.setUpdateTime(new Date());
            schedule.setCreateTime(new Date());
            schedule.setIsDeleted(0);
            scheduleRepository.save(schedule);
        }



    }




}
