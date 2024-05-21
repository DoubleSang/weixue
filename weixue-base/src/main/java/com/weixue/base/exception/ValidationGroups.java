package com.weixue.base.exception;

/**
 * @Author: Sang
 * @Date: 2024/05/03/21:24
 * @Description: 用于分组校验，定义一些常用的组
 */

public class ValidationGroups {
    /**
     * 校验规则：使用JSR303校验，spring-boot-validation
     * 底层使用Hibernate Validator
     * Hibernate Validator是Bean Validation 的参考实现
     */
    public interface Insert{};
    public interface Update{};
    public interface Delete{};
}
