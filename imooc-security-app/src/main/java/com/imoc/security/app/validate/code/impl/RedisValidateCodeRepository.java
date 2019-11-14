package com.imoc.security.app.validate.code.impl;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeRepository;
import com.imooc.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * @author schuke
 * @date 2019/11/8 19:23
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate <Object, Object> redisTemplate;



    @Override
    public void save(ServletWebRequest servletWebRequest, ValidateCode code, ValidateCodeType codeType) {
        redisTemplate.opsForValue().set(buildKey(servletWebRequest,codeType),code,30,TimeUnit.MINUTES);
    }

    @Override
    public Object get(ServletWebRequest servletWebRequest, ValidateCodeType codeType) {
        Object value = redisTemplate.opsForValue().get(buildKey(servletWebRequest, codeType));
        if (value == null) {
            return null;
        }
        return (ValidateCode)value;
    }

    @Override
    public void remove(ServletWebRequest servletWebRequest, ValidateCodeType codeType) {
        redisTemplate.delete(buildKey(servletWebRequest, codeType));
    }


    private String buildKey(ServletWebRequest servletWebRequest, ValidateCodeType codeType) {
        String deviceId = servletWebRequest.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code：" + codeType.toString().toLowerCase() + ":" + deviceId;
    }
}
