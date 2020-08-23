package com.jk.edu.controller;

import com.jk.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/edu/user")
@CrossOrigin   //解决跨域问题
@Api(description = "登录")
public class EduLoginController {
    //login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
