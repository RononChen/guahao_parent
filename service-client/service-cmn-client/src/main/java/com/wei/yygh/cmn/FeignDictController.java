package com.wei.yygh.cmn;


import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
@FeignClient(value = "service-cmn")
public interface FeignDictController {

    @ApiOperation(value = "查询医院等级")
    @GetMapping("/admin/cmn/dict/getHospitalType/{dictCode}/{value}")
    public String getHospitalType(@PathVariable("dictCode") String dictCode,
                                  @PathVariable("value") Long value);


    @ApiOperation(value = "查询地址")
    @GetMapping("/admin/cmn/dict/getAdderss/{value}")
    public String getAdderss(@PathVariable("value")Long value);
}
