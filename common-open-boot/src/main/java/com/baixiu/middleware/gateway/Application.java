package com.baixiu.middleware.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * @author baixiu
 * @date 创建时间 2023年12月19日
 */
@SpringBootApplication
public class Application  extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class);
        System.out.println ("middleware-gateway.test started");
    }


}
