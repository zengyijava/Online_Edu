package com.jk.edu.service;

import com.jk.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jk.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zy
 * @since 2020-05-11
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
