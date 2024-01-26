package com.baixiu.middleware.gateway.core;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * default router 实现
 * 可以在SPI SPIDefine rout 注解属性进行自定义扩展
 * @author baixiu
 * @date 创建时间 2023/12/7 8:22 PM
 */
@Component("defaultSPIRouter")
public class ThreadLocalSPIRouter implements SPIRouter{

    private static TransmittableThreadLocal<Stack<String>> ROUTE_IDENTITY=null;
    
    static {
        ROUTE_IDENTITY=TransmittableThreadLocal
                        .withInitial(()->{
                            Stack<String> stack=new Stack<>();
                            return stack;
                        });
    }

    @Override
    public List<String> route(MethodInvocation methodInvocation) {
        String routers= ROUTE_IDENTITY.get().peek();
        return Arrays.asList(routers.split(","));
    }

    public static String popIdentity(){
        return ROUTE_IDENTITY.get().pop();
    }

    public static void pushIdentity(String identity){
        ROUTE_IDENTITY.get().push(identity);
    }

}
