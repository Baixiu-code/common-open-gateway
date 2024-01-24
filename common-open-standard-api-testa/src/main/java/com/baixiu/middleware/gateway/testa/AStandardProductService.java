package com.baixiu.middleware.gateway.testa;


import com.baixiu.middleware.gateway.anno.Extension;
import com.baixiu.middleware.gateway.api.base.SyncRoutableRequest;
import com.baixiu.middleware.gateway.api.param.request.EtcProductRequest;
import com.baixiu.middleware.gateway.api.sku.StandardProductService;
import org.springframework.stereotype.Service;

/**
 * @author baixiu
 * @date 创建时间 2024/1/23 6:12 PM
 */
@Extension(appName="clientA")
public class AStandardProductService implements StandardProductService {
    @Override
    public void syncProduct(SyncRoutableRequest<EtcProductRequest> request) {
        System.out.println ("client a sync product success");
    }
    
}
