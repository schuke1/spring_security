package com.imooc.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author schuke
 * @date 2019/11/8 22:09
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
