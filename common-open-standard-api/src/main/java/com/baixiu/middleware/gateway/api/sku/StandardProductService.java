package com.baixiu.middleware.gateway.api.sku;

import com.baixiu.middleware.gateway.anno.SPIDefine;
import com.baixiu.middleware.gateway.api.base.SyncRoutableRequest;
import com.baixiu.middleware.gateway.api.param.request.EtcProductRequest;

/**
 * 标准商品同步服务 
 * @author baixiu
 * @date 2024年01月23日
 */
@SPIDefine
public interface StandardProductService {

    /**
     * sync test 
     * @param request request
     */
    void syncProduct(SyncRoutableRequest<EtcProductRequest> request); 
    
}
