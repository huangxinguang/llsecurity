package com.laile.security.admin.controller.auth;

import com.laile.esf.common.util.Page;
import com.laile.security.admin.controller.AbstractController;
import com.laile.security.admin.util.R;
import com.laile.security.core.model.auth.role.Role;
import com.laile.security.service.auth.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by huangxinguang on 2017/10/13 下午1:42.
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends AbstractController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/list")
    public ModelAndView list(Integer currentPage,Integer showCount,String searchKey) {
        ModelAndView mav = getModelAndView();
        Page<Role> page = roleService.queryRoleListPage(currentPage,showCount,searchKey);
        mav.addObject("page",page);
        mav.addObject("searchKey",searchKey);
        mav.setViewName("/auth/role/list");
        return mav;
    }

    @RequestMapping(value = "/add")
    public ModelAndView add() {
        ModelAndView mav = getModelAndView();
        mav.setViewName("/auth/role/add");
        return mav;
    }

    @RequestMapping(value = "/edit")
    public ModelAndView edit(Integer id) {
        ModelAndView mav = getModelAndView();
        Role role = roleService.selectByPrimaryKey(id);
        mav.addObject("role",role);
        mav.setViewName("/auth/role/edit");
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public Object saveOrUpdate(Role role,Integer[] menuIds) {
        if(role.getId() == null) {
            role.setCreateBy(getSessionUser().getAdminName());
            role.setCreateTime(new Date());
            role.setStatus(1);
        }else {
            role.setUpdateBy(getSessionUser().getAdminName());
            role.setUpdateTime(new Date());
        }
        roleService.saveOrUpdate(role, menuIds);
        return R.ok();
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Object del(Integer id) {
        roleService.delete(id);
        return R.ok();
    }
}
