package com.laile.security.admin.util;


import com.laile.security.admin.constant.WebConstant;
import com.laile.security.core.model.auth.admin.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * 权限处理
 */
public class Jurisdiction {

    /**
     * 获取当前登录的用户名
     *
     * @return
     */
    public static String getUsername() {
        Admin admin = (Admin) getSession().getAttribute(WebConstant.SESSION_USER);
        return admin.getAdminName();
    }

    /**
     * shiro管理的session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
}
