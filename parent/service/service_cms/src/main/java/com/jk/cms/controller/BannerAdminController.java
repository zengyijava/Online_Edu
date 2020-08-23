package com.jk.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.cms.entity.CrmBanner;
import com.jk.cms.service.CrmBannerService;
import com.jk.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-01
 */
@RestController
@RequestMapping("/educms/bannerAdmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @GetMapping("pageBanner/{current}/{size}")
    public R pageBanner(@PathVariable long current, @PathVariable long size){
        Page<CrmBanner> pageBanner=new Page<>(current,size);
        crmBannerService.page(pageBanner,null);
        return R.ok().data("records",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //2 添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }




}

