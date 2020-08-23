import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: '/edu/video/addVideo',
            method: 'post',
            data: video
          })
    },
    
    //删除小节
    deleteVideo(id) {
        return request({
            url: '/edu/video/'+id,
            method: 'delete'
          })
    },
    //删除视频
    deleteAliyunvod(id) {
        return request({
            url: '/eduvod/video/removeAlyVideo/'+id,
            method: 'delete'
          })
    }
}