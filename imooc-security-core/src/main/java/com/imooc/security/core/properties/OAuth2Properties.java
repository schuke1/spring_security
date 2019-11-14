package com.imooc.security.core.properties;


/**
 * @author schuke
 * @date 2019/11/8 23:58
 */
public class OAuth2Properties {

    private OAuth2ClientProperties[] clients = {};
    private String jwtSigningKey = "mrcode";

    public OAuth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(OAuth2ClientProperties[] clients) {
        this.clients = clients;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

}
