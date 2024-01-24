package com.baixiu.middleware.gateway.api.param.request;

import java.io.Serializable;

/**
 * 模拟商品同步的product request 
 * @author baixiu
 * @date: 2024年01月23日
 */
public class EtcProductRequest implements Serializable {

    /**
     * 商品id
     **/
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
