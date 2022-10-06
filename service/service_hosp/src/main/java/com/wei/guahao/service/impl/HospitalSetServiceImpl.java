package com.wei.guahao.service.impl;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.guahao.mapper.HospitalSetMapper;
import com.wei.guahao.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;

@Service
public class HospitalSetServiceImpl extends
        ServiceImpl<HospitalSetMapper, HospitalSet>
        implements HospitalSetService{

    @Autowired
    private HospitalSetMapper hospitalSetMapper;

//    通过医院编码 获取签名
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}
