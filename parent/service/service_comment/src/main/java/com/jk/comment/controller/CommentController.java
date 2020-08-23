package com.jk.comment.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.comment.client.UcenterClient;
import com.jk.comment.entity.Comment;
import com.jk.comment.service.CommentService;
import com.jk.commonutils.JwtUtils;
import com.jk.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
@Api(description = "评论模块")
@RestController
@RequestMapping("/comment/comment")
@CrossOrigin

public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "添加评论")
    @PostMapping("addComment/{courseId}")
    public R addComment(@PathVariable String courseId, HttpServletRequest request,
                        @RequestParam("content")String content){
        R r=commentService.addComment(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok();
    }

    @ApiOperation(value = "评论分页")
    @GetMapping("commentPageList/{current}/{size}")
    public R commentPageList(@PathVariable long current, @PathVariable long size,
                             @RequestBody(required = false)Comment comment){
        Page<Comment> commentPage=new Page<>(current,size);
        IPage<Comment> page = commentService.page(commentPage, null);
        long total = commentPage.getTotal();
        List<Comment> records = commentPage.getRecords();
        return R.ok().data("total",total).data("records",records);
    }



}

