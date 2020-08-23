package com.jk.edu.client;


import com.alibaba.excel.event.Order;
import com.jk.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFileDegradeFeignClient implements OrderClient {


    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        return false;
    }
}
