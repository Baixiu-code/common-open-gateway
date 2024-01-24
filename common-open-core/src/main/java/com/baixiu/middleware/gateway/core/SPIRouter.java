package com.baixiu.middleware.gateway.core;


import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

/**
 * 开放SPI router 接口用以实现方实现自定义router能力
 * @author baixiu
 * @date 2023年12月07日
 */
public interface SPIRouter {


    /**
     * route 通过上下文获取route
     * @param methodInvocation context of methodInvocation
     * @return
     */
    List<String> route(MethodInvocation methodInvocation);
}
