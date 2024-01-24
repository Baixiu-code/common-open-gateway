package com.baixiu.middleware.gateway.testa;

import com.baixiu.middleware.gateway.api.base.SyncRoutableRequest;
import com.baixiu.middleware.gateway.api.param.request.EtcProductRequest;
import org.springframework.stereotype.Service;

/**
 * 标准商品同步服务 
 * @author baixiu
 * @date 2024年01月23日
 */
@Service("standardSkuServiceA")
public class StandardSkuServiceA {

    /**
     * sync test
     *
     * @param request request
     */
    void syncProduct(SyncRoutableRequest<EtcProductRequest> request) {
        
    }

}
