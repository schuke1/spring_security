package com.imoc.security.app.social.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * @author schuke
 * @date 2019/11/8 21:52
 */
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private SocialUserDetailsService userDetailsService;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;


    public void configure(HttpSecurity http) throws Exception {

        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider).
                addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
