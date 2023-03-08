package com.fhyj.common.mybatis.plus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fhyj.common.base.service.LoginUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description
 *
 * @author djdgt
 * @since 2023/3/1 15:07
 */
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //数据库类型是MySql，因此参数填写DbType.MYSQL
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public DefaultSqlInjector defaultSqlInjector() {
        return new FhyjSqlInjector();
    }

    @Bean
    @ConditionalOnBean(LoginUserService.class)
    public MyMetaObjectHandler metaObjectHandler(LoginUserService loginUserService) {
        return new MyMetaObjectHandler(loginUserService);
    }

    @Bean
    @ConditionalOnMissingBean(LoginUserService.class)
    public MyMetaObjectHandler metaObjectHandler() {
        return new MyMetaObjectHandler(() -> 0);
    }
}
