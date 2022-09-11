package com.wei.yygh.common.exception;


import com.wei.yygh.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//ControllerAdvice的作用主要是配合@ExceptionHandler做异常处理
@ControllerAdvice
public class GlobalExceptionHandler  {

//    想要将结果以json的格式返回给前端 需要加上ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }


    //    想要将结果以json的格式返回给前端 需要加上ResponseBody
    @ExceptionHandler(YyghException.class)
    @ResponseBody
    public Result error(YyghException e){
        e.printStackTrace();

        return Result.fail();
    }

}
