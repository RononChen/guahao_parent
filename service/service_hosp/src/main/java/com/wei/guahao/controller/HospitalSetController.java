package com.wei.guahao.controller;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.guahao.service.HospitalSetService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

//    3、带有条件的分页查询 查询条件封装成queryVo类型的对象 所以用post请求
    @ApiOperation(value = "有条件的分页查询")
    @PostMapping("/selectPageHospitalSet/{currentPage}/{length}")
    public Result selectPageHospitalSet(@PathVariable Long currentPage,
                                        @PathVariable Long length,
                                        @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
//        3.1、封装page对象
        Page<HospitalSet> page = new Page(currentPage,length);

        QueryWrapper<HospitalSet> wrapper = new QueryWrapper();
//        eq是QueryWrapper的父类AbstractWrapper中的方法
//        模糊查询like
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
//        如果hosname不为空，在进行模块查询条件
        if(!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname",hosname);
        }
//        如果hoscode不为空，在进行模块的查询
        if(!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode",hoscode);
        }
//        要加上泛型
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);
        return Result.ok(hospitalSetPage);
    }



}
