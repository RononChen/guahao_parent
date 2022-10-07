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

//    导入HospitolRepository
    @Autowired
    private HospitolRepository hospitolRepository;

//    保存医院信息
    @Override
    public void save(Map<String, Object> map) {

//        1、使用fastjson将map转换为json字符串
        String s = JSONObject.toJSONString(map);
//        2、使用fastjson将json字符串转换为指定类型的实体
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);

//        3、根据医院实体的唯一索引Hoscode查看是否有Hospital
        Hospital hospitalExist = hospitolRepository
                .getHospitalByHoscode(hospital.getHoscode());

//        4、如果存在，进行修改
        if(hospitalExist != null) {
//            这四个字段是医院系统上传的map里没有的字段
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitolRepository.save(hospitalExist);
        } else {
//            5、如果不存在，进行添加
//            这四个字段是医院系统上传的map里没有的字段
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitolRepository.save(hospital);
        }
    }

//    根据传递过来的医院编码  获取医院信息
    @Override
    public Hospital getHospital(String hoscode) {
        Hospital hospital = hospitolRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }

}
