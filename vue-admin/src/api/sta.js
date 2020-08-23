import request from '@/utils/request'
export default {
    //1 生成统计数据
    createStaData(day) {
        return request({
            url: '/edusta/sta/registerCount/'+day,
            method: 'get'
          })
    },
    //2 获取统计数据
    getDataSta(searchObj) {
        return request({
            url: `/edusta/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
          })
    }
}