package com.wei.guahao.cmn.controller;


import com.atguigu.yygh.model.cmn.Dict;
import com.wei.guahao.cmn.service.DictService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("数据字典模块")
@RestController
@RequestMapping("/admin/cmn/dict")
//跨域访问
@CrossOrigin
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

    /**
     *  因为没有数据返回，所以返回值使用void
     * @param response  返回给前端的响应 在这里面可以做设置 下载文件。
     *                  使用HttpServletResponse对象实现文件的下载
     */
    @ApiOperation(value = "导出数据字典")
    @GetMapping("/exportDate")
    public void exportDate(HttpServletResponse response){
//        调用service层的方法
        dictService.exportDate(response);
    }
}
