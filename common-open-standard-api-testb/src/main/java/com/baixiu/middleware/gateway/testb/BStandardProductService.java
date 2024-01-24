package com.baixiu.middleware.gateway.testb;

import com.baixiu.middleware.gateway.anno.Extension;
import com.baixiu.middleware.gateway.api.base.SyncRoutableRequest;
import com.baixiu.middleware.gateway.api.param.request.EtcProductRequest;
import com.baixiu.middleware.gateway.api.sku.StandardProductService;
import org.springframework.stereotype.Service;

/**
 * @author chenfanglin1
 * @date 创建时间 2024/1/23 6:20 PM
 */
@Extension(appName="clientA")
public class BStandardProductService implements StandardProductService {
    @Override
    public void syncProduct(SyncRoutableRequest<EtcProductRequest> request) {
        System.out.println ("client b sync product success");
    }
}
