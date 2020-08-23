package com.jk.servicebase.exceptionhandler;


import com.jk.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j     //logback 日志文件打印
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)  //指定出现什么异常执行这个方法
    @ResponseBody  //为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)  //指定出现什么异常执行这个方法
    @ResponseBody  //为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(MyDefineException.class)  //指定出现什么异常执行这个方法
    @ResponseBody  //为了返回数据
    public R error(MyDefineException e){
        log.error(e.getMessage());  //将错误信息写到G盘  error日志文件中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }




}
