import request from '@/utils/request'
export default {
    //1 添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/addCourseInfo',
            method: 'post',
            data:courseInfo
          })
    },
    //2 查询所有讲师
    getListTeacher() {
        return request({
            url: '/edu/teacher/findAll',
            method: 'get'
          })
    },
     //根据课程id查询课程基本信息
     getCourseInfoId(id) {
        return request({
            url: '/edu/course/getCourseInfo/'+id,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: '/edu/course/updateCourseInfo',
            method: 'post',
            data: courseInfo
          })
    },
     //课程确认信息显示
     getPublihCourseInfo(id) {
        return request({
            url: '/edu/course/getPublishCourseInfo/'+id,
            method: 'get'
          })
    },
    //课程最终发布
    publihCourse(id) {
        return request({
            url: '/edu/course/publishCourse/'+id,
            method: 'post'
          })
    },
    //TODO 课程列表
    //课程最终发布
    // getListCourse() {
    //     return request({
    //         url: '/edu/course',
    //         method: 'get'
    //       })
    // },
    //按条件课程分页查询
    getTeacherListPage(current,size,courseQuery) {
      return request({
        //url: '/table/list/'+current+"/"+size,
        url: `/edu/course/findByPageCondition/${current}/${size}`,
        method: 'post',
        //params
        //后台使用RequestBody获取数据
        data:courseQuery
      })
    },
    //删除讲师
    deleteCourseId(id) {
      return request({
          url: `/edu/course/${id}`,
          method: 'delete'
        })
  }

}
