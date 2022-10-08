package com.wei.guahao.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.wei.guahao.repository.HospitolRepository;


import com.wei.guahao.service.HospitalService;

import com.wei.yygh.cmn.FeignDictController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class HospitalServiceImpl implements HospitalService {

//    导入HospitolRepository
    @Autowired
    private HospitolRepository hospitolRepository;

//    导入DictFeignClient
    @Autowired
    private FeignDictController feignDictController;



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

//    分页查询医院信息
    @Override
    public Page<Hospital> findHospitalPageList(Integer page,
                                               Integer limit,
                                               HospitalQueryVo hospitalQueryVo) {
//        分页信息
        Pageable pageable = PageRequest.of(page - 1,limit);

        Hospital hospital = new Hospital();
//        使用BeanUtils将hospitalQueryVo转换成实体hospital对象
        BeanUtils.copyProperties(hospitalQueryVo,hospital);

//        匹配的规则信息
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);

//        封装一个Example
        Example<Hospital> example = Example.of(hospital, exampleMatcher);
//        分页查询医院信息
        Page<Hospital> pageHospital = hospitolRepository.findAll(example, pageable);


        pageHospital.getContent().stream().forEach(item ->{
            this.getHospitalType(item);
        });


        return pageHospital;
    }


    private Hospital getHospitalType(Hospital hospital) {

        String hostypeStr = hospital.getHostype();
        long hostype = Long.parseLong(hostypeStr);
        String hostypeName = feignDictController.getHospitalType("Hostype", hostype);
        hospital.getParam().put("hostypeName",hostypeName);

        long city = Long.parseLong(hospital.getCityCode());
        long province = Long.parseLong(hospital.getProvinceCode());
        long district = Long.parseLong(hospital.getDistrictCode());
        String cityName = feignDictController.getAdderss(city);
        String provinceName = feignDictController.getAdderss(province);
        String districtName = feignDictController.getAdderss(district);

        String fullAddress = provinceName+cityName+districtName;

        hospital.getParam().put("fullAddress",fullAddress);

        return hospital;

    }

}
