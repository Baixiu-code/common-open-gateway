package com.baixiu.middleware.gateway;

import com.baixiu.middleware.gateway.api.base.SyncRoutableRequest;
import com.baixiu.middleware.gateway.api.param.request.EtcProductRequest;
import com.baixiu.middleware.gateway.api.sku.StandardProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;


/**
 * @author baixiu
 * @date 2024年01月23日
 */
@RunWith(SpringRunner.class)
@Configuration
@SpringBootTest(classes = Application.class)
public class BootTest {


    @Autowired
    private StandardProductService standardProductService;
    
    @Test
    public void testMultiBizInvoke(){
        
        SyncRoutableRequest<EtcProductRequest> syncRoutableRequest=new SyncRoutableRequest<> ();
        syncRoutableRequest.setTargetSystems (Arrays.asList ("testa","testb"));
        
        EtcProductRequest request=new EtcProductRequest ();
        request.setProductId ("testA&B both invoke");
        syncRoutableRequest.setBizReq(request);
        
        standardProductService.syncProduct (syncRoutableRequest);
        
    }

}
