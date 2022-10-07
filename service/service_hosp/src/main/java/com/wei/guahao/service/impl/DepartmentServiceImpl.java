package com.wei.guahao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.model.hosp.Department;
import com.atguigu.yygh.vo.hosp.DepartmentQueryVo;
import com.wei.guahao.repository.DepartmentRepository;
import com.wei.guahao.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service
public class DepartmentServiceImpl implements DepartmentService {

//    注入DepartmentRepository
    @Autowired
    private DepartmentRepository departmentRepository;


//    上传科室
    @Override
    public void saveDepartment(Map<String, Object> paramMap) {
//       1 保存科室 实体类是Department 想法将map转成Department
        String mapStr = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(mapStr, Department.class);

//        2 通过医院编号和科室编号查询是否有科室数据  有就更新 没有就保存
        Department departmentExist =
                departmentRepository.getDepartmentByHoscodeAndDepcode(
                        department.getHoscode(),department.getDepcode());

//        3 有数据 更新操作
        if (departmentExist != null){
            departmentExist.setUpdateTime(new Date());
            departmentExist.setIsDeleted(0);
            departmentRepository.save(departmentExist);

        }else {
//            没有数据保存操作
            department.setUpdateTime(new Date());
            department.setCreateTime(new Date());
            department.setIsDeleted(0);
            departmentRepository.save(department);
        }
    }


//    分页查询科室信息
    @Override
    public Page<Department> findDepartment(int page, int limit,
                                           DepartmentQueryVo departmentQueryVo) {
//        分页 模糊查询
//        分页
        Pageable pageable = PageRequest.of(page, limit);
//        查询的条件
        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);
//        模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
//        封装成一个Example
        Example<Department> example = Example.of(department, exampleMatcher);
        Page<Department> pageDepartment = departmentRepository.findAll(example, pageable);

        return pageDepartment;
    }

//    删除科室信息
    @Override
    public void removeDepartment(String hoscode, String depcode) {

//        首先是根据医院编码和科室编码查询下有没有科室信息 有才能删除
        Department department =
                departmentRepository.getDepartmentByHoscodeAndDepcode(hoscode, depcode);

        if (department != null){
            departmentRepository.deleteById(department.getId());
        }
    }


}
