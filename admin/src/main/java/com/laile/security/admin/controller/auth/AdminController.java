package com.laile.security.admin.controller.auth;

import com.laile.esf.common.util.Page;
import com.laile.security.admin.constant.WebConstant;
import com.laile.security.admin.controller.BaseController;
import com.laile.security.admin.util.IPUtils;
import com.laile.security.admin.util.R;
import com.laile.security.core.dto.AdminDto;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.model.auth.role.Role;
import com.laile.security.core.util.CipherHelper;
import com.laile.security.core.util.ConfigConst;
import com.laile.security.core.vo.admin.AdminVo;
import com.laile.security.service.auth.admin.IAdminService;
import com.laile.security.service.auth.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by huangxinguang on 2017/10/13 下午6:17.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/list")
    public ModelAndView list(Integer currentPage, Integer showCount, String searchKey) {
        ModelAndView mav = getModelAndView();
        Page<AdminVo> page = adminService.queryAdminListPage(currentPage,showCount,searchKey);
        mav.addObject("page",page);
        mav.addObject("searchKey",searchKey);
        mav.setViewName("/auth/admin/list");
        return mav;
    }

    @RequestMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mav = getModelAndView();
        List<Role> roleList = roleService.selectAll();
        mav.addObject("roleList",roleList);
        mav.setViewName("/auth/admin/add");
        return mav;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(Integer id) {
        ModelAndView mav = getModelAndView();
        Admin admin = adminService.selectByPrimaryKey(id);
        List<Role> roleList = roleService.selectAll();
        mav.addObject("roleList",roleList);
        mav.addObject("admin",admin);
        mav.setViewName("/auth/admin/edit");
        return mav;
    }

    @RequestMapping(value = "/showInfo")
    public ModelAndView showInfo() {
        ModelAndView mav = getModelAndView();
        Admin admin = adminService.selectByPrimaryKey(getSessionUser().getId());
        List<Role> roleList = roleService.selectAll();
        mav.addObject("roleList",roleList);
        mav.addObject("admin",admin);
        mav.setViewName("/auth/admin/edit");
        return mav;
    }

    @RequestMapping(value = "/changePwdPage")
    public ModelAndView changePwdPage() {
        Admin admin = getSessionUser();
        ModelAndView mav = getModelAndView();
        mav.addObject("admin",admin);
        mav.setViewName("/auth/admin/changePwd");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/changePwd")
    public Object changePwd(Integer id,String password,String newPassword) {
        adminService.changePwd(id, password, newPassword);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/unlock")
    public Object unlock(String password) {
        adminService.unlock(getSessionUser().getId(), password);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public Object saveOrUpdate(HttpServletRequest request, AdminDto admin, @RequestParam(value = "file",required = false) MultipartFile file) {
        if (file != null && file.getSize() > 0) {
            String imageUrl = this.uploadFile(getSessionUser(), file);
            admin.setHeadPic(imageUrl);
        }
        if(admin.getId() == null) {
            admin.setCreateBy(getSessionUser().getAdminName());
            admin.setCreateTime(new Date());
            admin.setStatus(1);
            admin.setLoginErrorCount(0);
            admin.setCreateIp(IPUtils.getIpAddr(request));
            admin.setUpdatePwdCount(0);
            admin.setLimitCount(WebConstant.LOGIN_LIMIT_COUNT);
            admin.setPassword(CipherHelper.encryptPassword(admin.getPassword(), ConfigConst.SOLT));
            admin.setUpdateBy(getSessionUser().getAdminName());
            admin.setUpdateTime(new Date());
        }else {
            admin.setUpdateBy(getSessionUser().getAdminName());
            admin.setUpdateTime(new Date());
        }

        adminService.saveOrUpdate(admin);
        return R.ok();
    }



    @ResponseBody
    @RequestMapping(value = "/del")
    public Object del(Integer id) {
        adminService.delete(id);
        return R.ok();
    }
}
