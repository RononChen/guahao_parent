package com.wei.guahao.cmn.controller;


import com.atguigu.yygh.model.cmn.Dict;
import com.wei.guahao.cmn.service.DictService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("数据字典模块")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据数据id查询子数据列表")
//    根据数据id查询子数据列表
    @GetMapping("/selectChildData/{id}")
    public Result selectChildData(@PathVariable long id){
        List<Dict> dictList =  dictService.selectChildData(id);
        return Result.ok(dictList);
    }
}
