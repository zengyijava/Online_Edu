package com.jk.edu.controller;


import com.jk.commonutils.R;
import com.jk.edu.entity.Chapter;
import com.jk.edu.entity.chapter.ChapterVo;
import com.jk.edu.service.ChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
@RestController
@RequestMapping("/edu/chapter")
@CrossOrigin
@Api(description = "章节管理")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}

