package com.jk.comment.service.impl;

import com.jk.comment.client.EduClient;
import com.jk.comment.client.UcenterClient;
import com.jk.comment.entity.Comment;
import com.jk.comment.mapper.CommentMapper;
import com.jk.comment.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jk.commonutils.R;
import com.jk.commonutils.ordervo.CourseWebVoOrder;
import com.jk.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author zy
 * @since 2020-06-20
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduClient eduClient;

    @Override
    public R addComment(String courseId, String memberId,String content) {
        //这里采用service_order 里面的  没有重新定义一个对象了，内容是一样的
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);
        Comment comment=new Comment();
        comment.setCourseId(courseInfoOrder.getId());
        comment.setTeacherId(courseInfoOrder.getTeacherId());
        comment.setMemberId(userInfoOrder.getId());
        comment.setNickname(userInfoOrder.getNickname());
        comment.setAvatar(userInfoOrder.getAvatar());
        comment.setContent(content);
        comment.setIsDeleted(0);
        baseMapper.insert(comment);
        return R.ok();
    }
}
