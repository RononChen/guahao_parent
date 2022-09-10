package com.wei.guahao.controller;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.wei.guahao.service.HospitalSetService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "这是医院设置类")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;


//    1、查询所有的医院信息
    @ApiOperation(value = "查询所有的医院信息")
    @GetMapping("/selectall")
    public Result selectAllHospital(){
//        显示所有
        List<HospitalSet> list = hospitalSetService.list();

        return Result.ok(list);
    }
//    2、逻辑删除 医院设置
    @ApiOperation(value = "逻辑删除 医院设置")
    @DeleteMapping("{id}")
    public Result removeHospitalSet(@PathVariable int id){
        boolean b = hospitalSetService.removeById(id);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

}
