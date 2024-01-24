package com.baixiu.middleware.gateway.core;

import com.baixiu.middleware.gateway.anno.Extension;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import java.util.Map;
import static com.baixiu.middleware.gateway.core.SPIExtensionBeanContexts.BEAN_EXTENDS_MAP;

/**
 * 用以扫描 extension 注解的内容 
 * @author baixiu
 * @date 创建时间 2024/1/23 5:58 PM
 */
@Component
public class ExtensionScanHandler implements ApplicationContextAware {
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,Object> extensionMaps=applicationContext.getBeansWithAnnotation (Extension.class);
        extensionMaps.forEach ((key,value)->{
            Extension extensionItem= AnnotationUtils.findAnnotation(value.getClass (),Extension.class);
            String realKey=extensionItem.appName()+"_"+extensionItem.scenario();
            SPIExtensionBeanContexts.BEAN_EXTENDS_MAP.put(realKey,value);
        });
    }
    
}
