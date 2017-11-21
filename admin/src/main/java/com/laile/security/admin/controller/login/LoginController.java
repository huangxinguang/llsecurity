package com.laile.security.admin.controller.login;


import com.laile.security.admin.constant.WebConstant;
import com.laile.security.admin.controller.BaseController;
import com.laile.security.admin.util.CaptchaUtil;
import com.laile.security.admin.util.Jurisdiction;
import com.laile.security.admin.util.R;
import com.laile.security.core.exception.TipsException;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.security.cipher.CipherHelper;
import com.laile.security.security.constant.SecurityConstant;
import com.laile.security.service.auth.admin.IAdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangxinguang on 2017/10/14 下午2:43.
 */
@Controller
@RequestMapping
public class LoginController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/login");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/loginIn")
    public Object loginIn(String adminName,String password,String captchaCode) {
        if (!CaptchaUtil.validCaptcha(captchaCode)) {
            throw new TipsException("验证码不正确");
        }
        Admin admin = adminService.getAdmin(adminName);

        if (admin == null) {
            throw new TipsException("用户名或密码不正确");
        }
        admin = adminService.login(admin.getAdminName(), CipherHelper.encryptPassword(password, SecurityConstant.SOLT));

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(adminName, password);
        try {
            token.setRememberMe(true);
            subject.login(token);
        } catch (AuthenticationException e) {
            throw new TipsException("用户名或密码不正确");
        }
        Session session = getSession();
        session.setAttribute(WebConstant.SESSION_USER, admin);// 把用户信息放session中
        session.removeAttribute(WebConstant.SESSION_SECURITY_CODE);// 清除登录验证码的session
        return R.ok();
    }

    @RequestMapping(value = "/loginOut")
    public ModelAndView loginOut() {
        ModelAndView mav = getModelAndView();
        Session session = Jurisdiction.getSession(); // 以下清除session缓存
        session.removeAttribute(WebConstant.SESSION_USER);
        // shiro销毁登录
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        mav.setViewName("/login");
        return mav;
    }
}
