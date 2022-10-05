package com.wei.guahao.controller.api;


import com.wei.guahao.service.HospitalService;
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

    @Autowired
    private HospitalService hospitalService;

    @PostMapping("/saveHospital")
    @ApiOperation(value = "保存医院")
    public Result saveHospital(HttpServletRequest request){
//        获取请求参数map
        Map<String, String[]> requestMap = request.getParameterMap();
//        将<String, String[]>类型的数组转换为<String, Object>类型的数组
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1 获取医院系统传递过来的签名,签名进行了MD5加密
        String hospSign = (String)paramMap.get("sign");
        //2 根据传递过来医院编码，查询医院设置数据库，查询签名
        String hoscode = (String)paramMap.get("hoscode");
//        String signKey = hospitalSetService.getSignKey(hoscode);
        //3 把数据库查询签名进行MD5加密
//        String signKeyMd5 = MD5.encrypt(signKey);
        //4 判断签名是否一致
//        if(!hospSign.equals(signKeyMd5)) {
//            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
//        }

        //小的图片使用base64编码，传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

//        进行保存
        hospitalService.save(paramMap);
        return Result.ok();
    }
}
