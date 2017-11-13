package com.laile.security.admin.controller.resource;

import com.laile.security.admin.controller.BaseController;
import com.laile.security.service.auth.resource.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by huangxinguang on 2017/9/27 下午2:55.
 */
@Controller
@RequestMapping("/menu")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    @ResponseBody
    @RequestMapping(value = "/menuTree")
    public Object menuTree() {
         return resourceService.getMenuTree(getSessionUser().getId());
    }

    @ResponseBody
    @RequestMapping(value = "/roleTree")
    public Object roleTree()  {
        return resourceService.getRoleTree();
    }

    @ResponseBody
    @RequestMapping(value = "/selectedRoleTree")
    public Object selectedRoleTree(Integer id)  {
        return resourceService.getSelectedRoleTree(id);
    }
}
