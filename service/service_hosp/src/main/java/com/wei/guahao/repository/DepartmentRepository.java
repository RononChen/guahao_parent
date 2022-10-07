package com.wei.guahao.repository;

import com.atguigu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {

//    通过医院编号和科室编号查询科室数据
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
