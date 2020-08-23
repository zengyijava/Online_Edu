package com.jk.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jk.commonutils.R;
import com.jk.edu.entity.Teacher;
import com.jk.edu.entity.vo.TeacherQuery;
import com.jk.edu.service.TeacherService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zy
 * @since 2020-05-03
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/edu/teacher")
@CrossOrigin   //解决跨域问题
public class TeacherController {
    //http://localhost:8001/edu/teacher/findAll
    @Autowired
    private TeacherService teacherService;

    /*
    查询所有
     */
    @ApiOperation(value = "显示所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<Teacher> list=teacherService.list(null);
        return R.ok().data("items",list);
    }
    /*
    删除
     */
    @ApiOperation(value = "根据讲师ID逻辑删除")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        boolean flag=teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页
    @ApiOperation(value = "分页查询")
    @GetMapping("findByPage/{current}/{size}")
    public R FindByPage(@PathVariable Long current,@PathVariable Long size){
        Page<Teacher> pageTeacher=new Page<>(current,size);
        teacherService.page(pageTeacher,null);
        Long total=pageTeacher.getTotal();
        List<Teacher> records=pageTeacher.getRecords();
//        Map map=new HashMap();
//        map.put("total",total);
//        map.put("records",records);
//        return R.ok().data(map);
        return R.ok().data("total",total).data("records",records);
    }

    //多条件分页查询
//    @ApiOperation(value = "多条件分页查询")
//    @GetMapping("findByPageCondition/{current}/{size}")
//    public R FindByPageCondition(@PathVariable Long current,@PathVariable Long size,
//                                 TeacherQuery teacherQuery){
//        Page<Teacher> pageTeacher=new Page<>(current,size);
//        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
//        String name= teacherQuery.getName();
//        Integer level=teacherQuery.getLevel();
//        String begin=teacherQuery.getBegin();
//        String end=teacherQuery.getEnd();
//        if(!StringUtils.isEmpty(name)){
//            wrapper.like("name",name);
//        }
//        if(!StringUtils.isEmpty(level)){
//            wrapper.eq("level",level);
//        }
//        if(!StringUtils.isEmpty(begin)){
//            wrapper.ge("gmt_create",begin);
//        }
//        if(!StringUtils.isEmpty(end)){
//            wrapper.le("gmt_create",end);
//        }
//        teacherService.page(pageTeacher,wrapper);
//        Long total=pageTeacher.getTotal();
//        List<Teacher> records=pageTeacher.getRecords();
//        return R.ok().data("total",total).data("records",records);
//    }

    /*
    @RequestBody  接受前段发送的json数据，并将json数据封装到对应的对象中去，
    必须使用post提交方法
     */
    @ApiOperation(value = "多条件分页查询")
    @PostMapping("findByPageCondition/{current}/{size}")
    public R FindByPageCondition(@PathVariable Long current,@PathVariable Long size,
                                 @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> pageTeacher=new Page<>(current,size);
        QueryWrapper<Teacher> wrapper=new QueryWrapper<>();
        String name= teacherQuery.getName();
        Integer level=teacherQuery.getLevel();
        String begin=teacherQuery.getBegin();
        String end=teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }
        //根据创建时间排序
        wrapper.orderByDesc("gmt_create");
        teacherService.page(pageTeacher,wrapper);
        Long total=pageTeacher.getTotal();
        List<Teacher> records=pageTeacher.getRecords();
        return R.ok().data("total",total).data("records",records);
    }

    //添加讲师接口
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean save=teacherService.save(teacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据讲师id进行查询
    @ApiOperation("根据讲师id进行查询")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
//        try {
//            //int i=1/0;        //测试异常
//        }catch (Exception e){
//            throw new MyDefineException(20001,"......执行了自定义异常处理......");
//        }

        Teacher teacher=teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

    //修改
    @ApiOperation("修改")
    @PutMapping("updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){
       boolean flag= teacherService.updateById(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

