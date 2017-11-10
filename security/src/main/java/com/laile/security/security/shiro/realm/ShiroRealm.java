package com.laile.security.security.shiro.realm;

import com.laile.esf.common.exception.BusinessException;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.util.ConfigConst;
import com.laile.security.security.exception.SecErrCode;
import com.laile.security.service.auth.admin.IAdminService;
import com.laile.security.service.auth.resource.IResourceService;
import com.laile.security.service.auth.role.IRoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author ectrip 2015-3-6
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    ShiroRealm() {
        logger.info("i'm loading");
    }

    @Autowired
    private IAdminService adminService;

    @Autowired
    public IRoleService roleService;

    @Autowired
    public IResourceService resourceService;

    /**
     * 登录信息和用户验证信息验证(non-Javadoc)
     * 
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String name = (String) token.getPrincipal(); // 得到用户名
        Admin admin = adminService.getAdmin(name);
        if (admin == null) {
            throw new BusinessException(SecErrCode.SEC001);// 没找到帐号 也提示账号密码错误
        }
        if (admin.getStatus() == 0) {
            throw new BusinessException(SecErrCode.SEC002);// 账号被锁定
        }
        // 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin.getAdminName(), // 用户名
                admin.getPassword(), // 密码
                ByteSource.Util.bytes(ConfigConst.SOLT),
                getName() // realm name
        );
        return authenticationInfo;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
     *
     * @see AuthorizingRealm#doGetAuthorizationInfo(PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        String name = (String) pc.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        Admin admin = adminService.getAdmin(name);
        authorizationInfo.setRoles(roleService.queryAdminRoleCodes(admin.getId()));
        authorizationInfo.setStringPermissions(resourceService.queryResourceCodes(admin.getId()));
        return authorizationInfo;
    }
}
