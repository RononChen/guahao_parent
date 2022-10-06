package com.wei.guahao.controller.api;


import com.atguigu.yygh.model.hosp.Hospital;
import com.wei.guahao.service.HospitalService;
import com.wei.guahao.service.HospitalSetService;
import com.wei.yygh.common.exception.YyghException;
import com.wei.yygh.common.helper.HttpRequestHelper;
import com.wei.yygh.common.result.Result;
import com.wei.yygh.common.result.ResultCodeEnum;
import com.wei.yygh.common.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/hosp")
@Api(tags = "这是医院类")
public class ApiController {

//    导入HospitalService
    @Autowired
    private HospitalService hospitalService;

//    导入HospitalService
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "查询医院")
    @PostMapping("/hospital/show")
    public Result getHospital(HttpServletRequest request){

//        1、获取请求参数map
        Map<String, String[]> requestMap = request.getParameterMap();
//        2、将<String, String[]>类型的数组转换为<String, Object>类型的数组
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //4 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospSign = (String)paramMap.get("sign");
        //5 根据传递过来医院编码，查询医院设置数据库，查询签名
        String hoscode = (String)paramMap.get("hoscode");
//        6 通过医院编码获取签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //7 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //8 判断签名是否一致 不一致抛出签名错误
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

//        9 根据传递过来的医院编码  获取医院信息
        Hospital hospital = hospitalService.getHospital(hoscode);

        return Result.ok(hospital);
    }

    @PostMapping("/saveHospital")
    @ApiOperation(value = "上传医院")
    public Result saveHospital(HttpServletRequest request){

//        1、获取请求参数map
        Map<String, String[]> requestMap = request.getParameterMap();
//        2、将<String, String[]>类型的数组转换为<String, Object>类型的数组
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //4 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospSign = (String)paramMap.get("sign");
        //5 根据传递过来医院编码，查询医院设置数据库，查询签名
        String hoscode = (String)paramMap.get("hoscode");
//        6 通过医院编码获取签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //7 把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);
        //8 判断签名是否一致 不一致抛出签名错误
        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //9 小的图片使用base64编码，传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

//        3、进行保存
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
