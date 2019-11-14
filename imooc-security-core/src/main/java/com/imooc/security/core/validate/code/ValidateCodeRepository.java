package com.imooc.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author schuke
 * @date 2019/11/8 19:02
 */
public interface ValidateCodeRepository {


    /**
     * 保存验证码
     * @param servletWebRequest
     * @param code
     * @param codeType
     */
    void save(ServletWebRequest servletWebRequest, ValidateCode code, ValidateCodeType codeType);


    /**
     * 获取验证码
     *
     * @param servletWebRequest
     * @param codeType
     * @return
     */
    Object get(ServletWebRequest servletWebRequest, ValidateCodeType codeType);

    /**
     * 移除验证码
     * @param servletWebRequest
     * @param codeType
     */
    void remove(ServletWebRequest servletWebRequest, ValidateCodeType codeType);
}
