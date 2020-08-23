package com.jk.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //启动类不加载数据库配置
@ComponentScan(basePackages = {"com.jk"})  //api  swagger
@EnableDiscoveryClient    //nacos注册中心
public class OssAplication {
    public static void main(String[] args) {
        SpringApplication.run(OssAplication.class, args);
    }
}
