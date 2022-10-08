package com.wei.guahao.cmn.controller;


import com.atguigu.yygh.model.cmn.Dict;
import com.wei.guahao.cmn.service.DictService;
import com.wei.yygh.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典模块")
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

//    文件上传使用Spring的MultipartFile
    @ApiOperation(value = "导入数据字典")
    @PostMapping("/importData")
    public Result importData(MultipartFile file){
        dictService.importData(file);
        return Result.ok();
    }

    @ApiOperation(value = "查询医院等级")
    @GetMapping("/getHospitalType/{dictCode}/{value}")
    public String getHospitalType(@PathVariable("dictCode") String dictCode,
                                  @PathVariable("value") Long value){
        String hospitalType = dictService.getName(dictCode,value);
        return hospitalType;
    };

    @ApiOperation(value = "查询地址")
    @GetMapping("/getAdderss/{value}")
    public String getAdderss(@PathVariable("value")Long value){
        String hospitalAddress = dictService.getName("",value);
        return hospitalAddress;
    };

    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping(value = "/findByDictCode/{dictCode}")
    public Result<List<Dict>> findByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return Result.ok(dictList);
    }

}
