package com.imoc.security.app.social.openid;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author schuke
 * @date 2019/11/8 21:30
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private SocialUserDetailsService userDetailsService;

    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;

        Set <String> providerUserIds = new HashSet <>();
        providerUserIds.add((String) authentication.getPrincipal());
        Set <String> userIds = usersConnectionRepository.findUserIdsConnectedTo(authenticationToken.getProviderId(),providerUserIds);

        if (CollectionUtils.isEmpty(userIds) || userIds.size() != 1) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        String userId = userIds.iterator().next();

        UserDetails userDetails = userDetailsService.loadUserByUserId(userId);

        if (userDetails == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        OpenIdAuthenticationToken authenticationTokenResult = new OpenIdAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationTokenResult.setDetails(authenticationToken.getDetails());

        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class <?> authenticationToken) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authenticationToken);
    }


    public SocialUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public UsersConnectionRepository getUsersConnectionRepository() {
        return usersConnectionRepository;
    }

    public void setUsersConnectionRepository(UsersConnectionRepository usersConnectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
    }
}
