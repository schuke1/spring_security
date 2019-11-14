package com.imooc.security.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * 自定义权限控制接口
 * @author schuke
 * @date 2019/11/14 2:20
 */
public interface AuthorizeConfigProvider {

    /**
     * @param config
     * @see HttpSecurity#authorizeRequests()
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
