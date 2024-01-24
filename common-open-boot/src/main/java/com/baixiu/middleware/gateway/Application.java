package com.baixiu.middleware.gateway;

import com.baixiu.middleware.gateway.anno.RouterBaseScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


/**
 * application main 
 * @author baixiu
 * @date 创建时间 2023年12月19日
 */
@RouterBaseScan(path = "com.baixiu.middleware.gateway")
@SpringBootApplication(scanBasePackages={"com.baixiu.middleware.gateway"})
public class Application  extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class);
        System.out.println ("middleware-gateway.test started");
    }


}
