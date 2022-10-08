package com.wei.guahao.controller;


import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.vo.hosp.HospitalQueryVo;
import com.wei.guahao.service.HospitalService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Api(tags = "医院管理类")
@RestController
@RequestMapping("/admin/hosp/hospital")
//跨域访问
@CrossOrigin
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    @ApiOperation(value = "医院列表")
    @GetMapping("/hospitalList/{page}/{limit}")
    public Result listHospital(@PathVariable("page") Integer page,
                               @PathVariable("limit")Integer limit,
                               HospitalQueryVo hospitalQueryVo){
//        分页查询医院信息
     Page<Hospital> hospitalPage = hospitalService.findHospitalPageList(page,limit,hospitalQueryVo);

     return Result.ok(hospitalPage);
    }

}
