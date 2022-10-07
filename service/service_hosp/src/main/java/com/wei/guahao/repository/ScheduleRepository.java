package com.wei.guahao.repository;

import com.atguigu.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {

//    通过医院编号和排班编号查询是否有排班数据
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
