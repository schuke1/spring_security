package com.imooc.security.browser.validdate.code.impl;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeRepository;
import com.imooc.security.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author schuke
 * @date 2019/11/8 19:09
 */
@Component
public class SeesionValidateCodeRepository implements ValidateCodeRepository {

    /**
     * 验证码放入session时的前缀
     */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 保存验证码
     * @param servletWebRequest
     * @param code
     * @param codeType
     */
    @Override
    public void save(ServletWebRequest servletWebRequest, ValidateCode code, ValidateCodeType codeType) {
        sessionStrategy.setAttribute(servletWebRequest,getSessionKey(servletWebRequest,codeType), codeType);
    }

    /**
     * 获取验证码
     * @param servletWebRequest
     * @param codeType
     * @return
     */
    @Override
    public ValidateCode get(ServletWebRequest servletWebRequest, ValidateCodeType codeType) {
        return (ValidateCode) sessionStrategy.getAttribute(servletWebRequest, getSessionKey(servletWebRequest, codeType));
    }

    /**
     * 移除验证码
     * @param servletWebRequest
     * @param codeType
     */
    @Override
    public void remove(ServletWebRequest servletWebRequest, ValidateCodeType codeType) {
        sessionStrategy.removeAttribute(servletWebRequest,getSessionKey(servletWebRequest,codeType));
    }

    /**
     * 构建验证码放入session时的key
     * @param request
     * @param codeType
     * @return
     */
    private String getSessionKey(ServletWebRequest request,ValidateCodeType codeType) {
        return SESSION_KEY_PREFIX + codeType.toString().toUpperCase();
    }

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

}
