package com.imoc.security.app.social.openid;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;


/**
 * @author schuke
 * @date 2019/11/8 21:02
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serivalVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 身份
     */
    private final Object principal;

    /**
     * 服务提供商
     */
    private String providerId;

    public OpenIdAuthenticationToken(String openId, String providerId) {
        super(null);
        this.principal = openId;
        this.providerId = providerId;
        setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object openId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = openId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public static long getSerivalVersionUID() {
        return serivalVersionUID;
    }
}
