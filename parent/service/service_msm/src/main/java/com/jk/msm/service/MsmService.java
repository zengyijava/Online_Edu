package com.jk.msm.service;

import java.util.Map;

public interface MsmService {
    boolean send(Map<String,Object> map, String phone);
}
