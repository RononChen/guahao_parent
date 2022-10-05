package com.wei.guahao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.model.hosp.Hospital;
import com.wei.guahao.repository.HospitolRepository;
import com.wei.guahao.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitolRepository hospitolRepository;

//    保存医院信息
    @Override
    public void save(Map<String, Object> map) {
//        使用fastjson将map转换为json字符串
        String s = JSONObject.toJSONString(map);
//        使用fastjson将json字符串转换为指定类型的实体
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);

//        hospitolRepository根据医院编码Hoscode查看是否有Hospital
        Hospital hospitalExist = hospitolRepository
                .getHospitalByHoscode(hospital.getHoscode());
        //如果存在，进行修改
        if(hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitolRepository.save(hospital);
        } else {//如果不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitolRepository.save(hospital);
        }

    }


}
