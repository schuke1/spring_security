package com.imoc.security.app.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author schuke
 * @date 2019/11/13 18:55
 */
public class ImoocJwtTokenEnhancer implements TokenEnhancer {


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        info.put("company", "imooc");

        // 设置附加信息
        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);

        return oAuth2AccessToken;
    }
}
