package com.baixiu.middleware.gateway.core;

import com.baixiu.middleware.gateway.anno.RouterBaseScan;
import com.baixiu.middleware.gateway.anno.SPIDefine;
import com.baixiu.middleware.gateway.consts.CommonConsts;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Proxy;
import java.util.*;

/**
 * 用以注册spring容器之外的bean定义。
 * @author baixiu
 * @date 创建时间 2023/12/20 2:35 PM
 */
@Component
public class MultiBizProxyRegister implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware, BeanFactoryAware, EnvironmentAware, ResourceLoaderAware {

    private BeanFactory beanFactory;
    
    private Environment envirnoment;
    
    private ClassLoader classLoader;
    
    private ResourceLoader resourceLoader;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {

        //step1:根据 baseScan 注解配置 获取需要扫描的basePackage 路径
        Set<String> scanPackages= getScanPackages(annotationMetadata);

        //step2:定义scanner
        ClassPathScanningCandidateComponentProvider spiScanner=getScanner();

        //遍历需要扫描的包路径，获取 beanDefinition
        for (String scanPackage : scanPackages) {
            //获取某一个路径下的候选bean定义
            Set<BeanDefinition> beanDefinitions= spiScanner.findCandidateComponents(scanPackage);
            //遍历每个 bean 注册定义，生产动态代理对象，通过 ImportBeanDefinitionRegistrar 开放的接口registerBeanDefinitions 进行
            //动态代理对象的注册到 spring 容器
            try {
                for (BeanDefinition beanDefinition : beanDefinitions) {

                    //当注册对象实例为注解bean定义时 获取注解的元数据信息
                    AnnotationMetadata annotationMetadataItem=null;
                    if(beanDefinition instanceof AnnotatedBeanDefinition){
                        annotationMetadataItem=((AnnotatedBeanDefinition) beanDefinition).getMetadata();
                    }

                    //通过注解定义的元数据信息获取注解对应的注解属性值
                    Map<String,Object> spiDefineAttrs=annotationMetadataItem.getAnnotationAttributes(SPIDefine.class.getName ());
                    if(spiDefineAttrs ==null || spiDefineAttrs.isEmpty ()){
                        continue;
                    }
                    String routerBeanName=null;
                    if(spiDefineAttrs.get(CommonConsts.SPI_DEFINE_ATTR_NAME) instanceof String){
                        routerBeanName = (String) spiDefineAttrs.get(CommonConsts.SPI_DEFINE_ATTR_NAME);
                    }

                    //get router
                    SPIRouter spiRouter=beanFactory.getBean(routerBeanName,SPIRouter.class);

                    //创建beanFactory
                    ExtensionBeanInterceptor interceptor=this.beanFactory.getBean(ExtensionBeanInterceptor.class);

                    //创建动态代理
                    Class<?> clazz=Class.forName(beanDefinition.getBeanClassName());
                    interceptor.setServiceInterface(clazz);
                    interceptor.setSpiRouter(spiRouter);
                    Object spiProxyObject=interceptor.getObject();
                    BeanDefinitionBuilder beanDefinitionBuilder=BeanDefinitionBuilder.genericBeanDefinition(spiProxyObject.getClass());
                    beanDefinitionBuilder.addConstructorArgValue (Proxy.getInvocationHandler(spiProxyObject));
                    AbstractBeanDefinition realBeanDefinition = beanDefinitionBuilder.getBeanDefinition();
                    realBeanDefinition.setPrimary(true);

                    //注册definition到spring容器
                    StringBuilder sb = new StringBuilder()
                            .append(clazz.getSimpleName())
                            .append("#Proxy");

                    //ImportBeanDefinitionRegistrar 重写得到的registry来实现bean的动态代理注册
                    registry.registerBeanDefinition(sb.toString(), realBeanDefinition);

                }
            } catch (Exception e) {
                throw new RuntimeException (e);
            }
        }
    }

    private ClassPathScanningCandidateComponentProvider getScanner() {
        //不通过默认filter来实现scanner ，则后面需要增加filter
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider (false, this.envirnoment) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition annotatedBeanDefinition) {
                //获取注解的元数据
                // 1.判断是否是独立的注解 不依赖其他注解或者类
                // 2.注解是否是一个接口
                if (annotatedBeanDefinition.getMetadata ().isIndependent () && annotatedBeanDefinition.getMetadata ().isInterface ()) {
                    //获取类 判断是否存在SPI defines
                    try {
                        Class<?> target = ClassUtils.forName(annotatedBeanDefinition.getMetadata ().getClassName (),
                                MultiBizProxyRegister.this.classLoader);
                        SPIDefine[] spiDefines = target.getAnnotationsByType (SPIDefine.class);
                        return spiDefines.length > 0;
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException (e);
                    }
                }
                return false;
            }
        };
        scanner.setResourceLoader(this.resourceLoader);
        scanner.addIncludeFilter(new AnnotationTypeFilter (SPIDefine.class));
        return scanner;
    }

    /**
     * 获取需要扫描得到beanDefinition的 package 路径
     * @param annotationMetadata 注解源数据信息 可获取spring中的注解上下文
     * @return
     */
    private Set<String> getScanPackages(AnnotationMetadata annotationMetadata){
        AnnotationAttributes annotationAttributes=AnnotationAttributes.fromMap(annotationMetadata
                .getAnnotationAttributes(RouterBaseScan.class.getName()));
        Set<String> spiScanPackages=null;
        assert annotationAttributes != null;
        String[] paths=annotationAttributes.getStringArray("path");
        if(paths.length>0){
            spiScanPackages=new HashSet<>();
            spiScanPackages.addAll(Arrays.asList(paths));
        }
        return spiScanPackages;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader=classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory=beanFactory;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.envirnoment=environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader=resourceLoader;
    }
}
