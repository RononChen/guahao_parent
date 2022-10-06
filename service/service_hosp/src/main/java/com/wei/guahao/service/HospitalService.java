package com.wei.guahao.service;


import com.atguigu.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

//    保存医院信息
    void save(Map<String, Object> map);

//    根据传递过来的医院编码  获取医院信息
    Hospital getHospital(String hoscode);
}
