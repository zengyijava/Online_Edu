package com.jk.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.cms.entity.CrmBanner;
import com.jk.cms.service.CrmBannerService;
import com.jk.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-01
 */
@Api(description = "前度首页")
@RestController
@RequestMapping("/educms/bannerFront")
@CrossOrigin
public class BannerFrontController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<CrmBanner> list=crmBannerService.selectAllBanner();
        return R.ok().data("list",list);
    }




}

