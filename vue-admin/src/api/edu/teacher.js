import request from '@/utils/request'

export default{
     getTeacherListPage(current,size,teacherQuery) {
        return request({
          //url: '/table/list/'+current+"/"+size,
          url: `/edu/teacher/findByPageCondition/${current}/${size}`,
          method: 'post',
          //params
          //后台使用RequestBody获取数据
          data:teacherQuery
        })
      },
      //删除讲师
    deleteTeacherId(id) {
        return request({
            url: `/edu/teacher/${id}`,
            method: 'delete'
          })
    },
     //添加讲师
     addTeacher(teacher) {
        return request({
            url: `/edu/teacher/addTeacher`,
            method: 'post',
            data: teacher
          })
    },
    //根据id查询讲师
    getTeacherInfo(id) {
        return request({
            url: `/edu/teacher/getTeacher/${id}`,
            method: 'get'
          })
    },
    //修改讲师
    updateTeacherInfo(teacher) {
        return request({
            url: `/edu/teacher/updateTeacher`,
            method: 'put',
            data: teacher
          })
    }
}
