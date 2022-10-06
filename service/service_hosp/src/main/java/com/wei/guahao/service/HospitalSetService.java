package com.wei.guahao.service;

import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.guahao.service.impl.HospitalSetServiceImpl;

public interface HospitalSetService extends IService<HospitalSet> {

//    通过医院编码获取签名
    String getSignKey(String hoscode);
}
