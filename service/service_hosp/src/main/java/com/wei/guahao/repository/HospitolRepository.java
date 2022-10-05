package com.wei.guahao.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HospitolRepository extends MongoRepository<Hospital,String> {

//    根据hoscode查找Hospital。继承MongoRepository，遵循Spring Data规范，会自动帮我们完成
    Hospital getHospitalByHoscode(String hoscode);
}
