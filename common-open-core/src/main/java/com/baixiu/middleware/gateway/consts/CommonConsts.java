package com.baixiu.middleware.gateway.consts;

/**
 * 常量
 * @author baixiu
 * @date 2023年12月08日
 */
public final class CommonConsts {

    /**
     * 默认扩展点注解 app 属性默认值
     */
    public final static String DEFAULT_EXTENSION_APP="DEFAULT_EXTENSION_APP";

    /**
     * 默认扩展点注解 scenario 属性默认值
     */
    public final static String DEFAULT_SCENARIO="DEFAULT_SCENARIO";

    /**
     * spi scan annotation value str
     */
    public static final String BASE_PACKAGES_STR = "basePackages";

    /**
     * 默认属性字段
     */
    public static final String DEFAULT_IDENTITY_FIELD_NAME="identityField";

    /**
     * multi biz router field name .
     */
    public static final String DEFAULT_TARGET_FIELD_NAME="targetSystems";

    /**
     *
     * @link{com.baixiu.middleware.spi.annotation.SPIDefine}
     */
    public static final String SPI_DEFINE_ATTR_NAME="router";

}
