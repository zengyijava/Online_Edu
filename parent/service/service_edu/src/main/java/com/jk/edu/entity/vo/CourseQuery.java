package com.jk.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程名称，模糊查询")
    private String title;

    @ApiModelProperty(value = "课程状态,已发布或未发布")
    private String status;
}
