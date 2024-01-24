package com.baixiu.middleware.gateway.api.base;


import java.io.Serializable;
import java.util.List;

/**
 *  发布接口必须的系统参数
 *  @date 2024年01月23日
 *  @author baixiu
 */

public class SyncRoutableRequest<T> implements Serializable {

    /**
     * 调用方系统
     */
    private String origSystem;

    /**
     * 目的系统
     */
    private List<String> targetSystems;


    /**
     * 业务参数
     */
    private T bizReq;

    public String getOrigSystem() {
        return origSystem;
    }

    public void setOrigSystem(String origSystem) {
        this.origSystem = origSystem;
    }


    public List<String> getTargetSystems() {
        return targetSystems;
    }

    public void setTargetSystems(List<String> targetSystem) {
        this.targetSystems = targetSystem;
    }

   
    public T getBizReq() {
        return bizReq;
    }

    public void setBizReq(T bizReq) {
        this.bizReq = bizReq;
    }


}
