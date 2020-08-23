package com.jk.comment.service;

import com.jk.comment.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jk.commonutils.R;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
public interface CommentService extends IService<Comment> {

    R addComment(String courseId, String memberIdByJwtToken,String content);
}
