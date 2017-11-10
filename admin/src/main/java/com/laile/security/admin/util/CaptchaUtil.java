package com.laile.security.admin.util;

import com.laile.security.admin.constant.WebConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class Describe
 * <p>
 * User: yangguang Date: 17/7/18 Time: 下午5:56
 */
public class CaptchaUtil {
    private static final Logger logger = LoggerFactory.getLogger(CaptchaUtil.class);

    public static boolean validCaptcha(String code) {
        logger.info("valid captcha code:{}", code);
        Session session = Jurisdiction.getSession();
        String sessionCode = (String) session.getAttribute(WebConstant.SESSION_SECURITY_CODE); // 获取session中的验证码
        if (StringUtils.isEmpty(code)) {
            logger.info("valid captcha code faiture beacuse code is empty");
            return false;
        }
        if (StringUtils.isEmpty(sessionCode)) {
            logger.info("session valid captcha code  is empty");
            return false;
        }
        if (sessionCode.equalsIgnoreCase(code)) {
            return true;
        }
        return false;
    }
}
