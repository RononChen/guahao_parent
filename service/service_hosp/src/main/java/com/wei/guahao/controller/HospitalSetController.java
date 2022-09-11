package com.wei.guahao.controller;


import com.atguigu.yygh.model.hosp.HospitalSet;
import com.atguigu.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wei.guahao.service.HospitalSetService;
import com.wei.yygh.common.result.Result;
import com.wei.yygh.common.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;
import java.util.Random;

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

    @ApiOperation(value = "添加医院设置")
//    4、添加医院设置   请求过来的时候封装成一个HospitalSet
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
//        4.1、签名秘钥 和 状态
        Random random = new Random();
//        使用MD5进行加密处理  需要添加MD5工具类
        String encrypt = MD5.encrypt(random.nextInt(100) + "" + System.currentTimeMillis());
        hospitalSet.setSignKey(encrypt);
//        设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        hospitalSetService.save(hospitalSet);
        return  Result.ok();
    }

//    5、根据id获取 医院设置
    @ApiOperation(value = "根据id获取 医院设置")
    @GetMapping("{id}")
    public Result selectHospitalSetById(@PathVariable long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

//    6、修改医院设置 也就是更新  需要传递进来一个封装好的HospitalSet
    @ApiOperation(value = "修改医院设置")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
//        根据传递进来的hospitalSet进行更新
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

//    7、批量删除 医院设置
    @ApiOperation(value = "批量删除 医院设置")
    @DeleteMapping("/benchRemoveHospitalSet")
    public  Result benchRemoveHospitalSet(@RequestBody List<Long> list){
        boolean b = hospitalSetService.removeByIds(list);
        if (b){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    /**
     *  8、医院设置  锁定和解锁  status字段进行状态操作   1 使用 0 不能使用
     *  先根据id来查询出来具体的医院  然后设置医院的status  最后更新这个医院设置
     */
    @ApiOperation(value = "医院设置  锁定和解锁")
    @PutMapping("/changeHospitalSetStatus/{id}/{status}")
    public Result changeHospitalSetStatus(@PathVariable Long id,
                                          @PathVariable Integer status){
//        8.1、根据id查询 医院设置
        HospitalSet hospitalSet = hospitalSetService.getById(id);
//        8.2、修改医院设置的状态
        hospitalSet.setStatus(1);
//        8.3、更新这个  医院设置
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    //9 发送签名秘钥  发送短信的时候再做具体操作
    @ApiOperation(value = "发送签名秘钥  发送短信的时候再做具体操作")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }

}
