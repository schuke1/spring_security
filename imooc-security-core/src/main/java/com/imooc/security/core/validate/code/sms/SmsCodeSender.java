/**
 * 
 */
package com.imooc.security.core.validate.code.sms;

/**
 * @author schuke
 *
 */
public interface SmsCodeSender {
	
	void send(String mobile, String code);

}
