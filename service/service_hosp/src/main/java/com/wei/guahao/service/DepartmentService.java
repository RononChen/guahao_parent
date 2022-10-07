package com.wei.guahao.service;

import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface DepartmentService {

//    上传科室
    void saveDepartment(Map<String, Object> paramMap);

//    分页查询科室信息
    Page<Department> findDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);

//    删除科室信息
    void removeDepartment(String hoscode, String depcode);
}
