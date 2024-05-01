package com.weixue.content.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Sang
 * @Date: 2024/04/19/17:29
 * @Description:
 */
@Configuration
@MapperScan("com.weixue.content.mapper")
public class MybatisPlusConfig {

    /**
    * @Description: 定义分页拦截器
    * @Param: []
    * @return: com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
    * @Author: Sang
    * @Date: 2024/4/19
    */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
