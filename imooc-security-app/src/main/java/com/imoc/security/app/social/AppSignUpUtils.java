package com.imoc.security.app.social;

import com.imoc.security.app.AppConstants;
import com.imoc.security.app.AppSecretException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author schuke
 * @date 2019/11/8 23:15
 */
public class AppSignUpUtils {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    // 目前为止都是自动配置的，直接获取即可
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    public void saveConnection(ServletWebRequest request, ConnectionData connectionData) {
        redisTemplate.opsForValue().set(buildKey(request), connectionData);
    }

    /**
     * @param userId
     * @param request
     * @see ProviderSignInAttempt#addConnection(java.lang.String, org.springframework.social.connect.ConnectionFactoryLocator, org.springframework.social.connect.UsersConnectionRepository)
     */
    public void doPostSignUp(String userId, ServletWebRequest request) {
        String key = buildKey(request);
        ConnectionData connectionData = (ConnectionData) redisTemplate.opsForValue().get(key);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(getConnection(connectionFactoryLocator, connectionData));
    }

    public Connection<?> getConnection(ConnectionFactoryLocator connectionFactoryLocator, ConnectionData connectionData) {
        return connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId()).createConnection(connectionData);
    }

    private String buildKey(ServletWebRequest request) {
        String deviceId = request.getHeader(AppConstants.DEFAULT_HEADER_DEVICE_ID);
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException("设备id参数不能为空");
        }
        return "imooc:security:social.connect." + deviceId;
    }
}
