package com.imoc.security.app.social.openid;

import com.imooc.security.core.properties.SecurityConstants;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author schuke
 * @date 2019/11/8 21:14
 */
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String openIdParameter = SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX;
    private String providerIdParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_PROVIDERID;
    private boolean postOnly = true;

    public OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (postOnly && httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supporter:" + httpServletRequest.getMethod());
        }

        String openId = obtainOpenId(httpServletRequest);
        String providerId = obtainProviderId(httpServletRequest);

        if (openId == null) {
            openId = "";
        }

        if (providerId == null) {
            providerId = "";
        }

        openId = openId.trim();
        providerId = providerId.trim();

        OpenIdAuthenticationToken authenticationToken = new OpenIdAuthenticationToken(openId, providerId);

        setDetails(httpServletRequest, authenticationToken);

        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainOpenId(HttpServletRequest request) {
        return request.getParameter(openIdParameter);
    }

    private String obtainProviderId(HttpServletRequest request) {
        return request.getParameter(providerIdParameter);
    }
}
