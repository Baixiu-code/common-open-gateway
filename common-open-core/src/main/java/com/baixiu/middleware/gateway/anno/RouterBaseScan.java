package com.baixiu.middleware.gateway.anno;

import com.baixiu.middleware.gateway.core.MultiBizProxyRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Target 标识在类型上。类，枚举等
 * @Retention
 *   runtime 在运行时生效
 *   source 在源代码级别生效不深入到编译字节码中
 *   class 在编译期会在字节码中生效
 * 用以扫描需要进行动态代理的package路径
 * @author baixiu
 * @date 2023年12月19日
 */
@Documented
@Target (ElementType.TYPE)
@Retention (RetentionPolicy.RUNTIME)
@Import(value = MultiBizProxyRegister.class)
public @interface RouterBaseScan {
     String path() default "";
     
}
