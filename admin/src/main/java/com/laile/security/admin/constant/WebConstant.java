package com.laile.security.admin.constant;

import com.laile.esf.common.config.PropertyPlaceholderConfigurer;

/**
 * Created by huangxinguang on 2017/10/31 下午2:37.
 */
public class WebConstant {

    /**
     * 验证码
     */
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";

    /**
     * ession用的用户
     */
    public static final String SESSION_USER = "sessionUser";
    /**
     * 登陆错误次数限制
     */
    public static Integer LOGIN_LIMIT_COUNT = Integer.valueOf(PropertyPlaceholderConfigurer.getProperty("loginLimitCount"));
}
