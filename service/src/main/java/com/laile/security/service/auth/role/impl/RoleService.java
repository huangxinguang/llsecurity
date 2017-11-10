package com.laile.security.service.auth.role.impl;

import com.laile.esf.common.util.Page;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.delegate.auth.role.RoleDelegate;
import com.laile.security.core.exception.TipsException;
import com.laile.security.core.model.auth.role.Role;
import com.laile.security.service.auth.role.IRoleService;
import com.laile.security.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:16.
 */
@Service
public class RoleService extends AbstractService<Role,Integer> implements IRoleService {
    @Autowired
    private RoleDelegate roleDelegate;

    @Override
    public Page<Role> queryRoleListPage(Integer currentPage, Integer showCount, String searchKey) {
        return roleDelegate.queryRoleListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(Role role, Integer[] menuIds) {
        Boolean exist = roleDelegate.querRoleCodeExist(role.getRoleCode());
        if(exist) {
            throw new TipsException("亲，此角色编码已经存在了。");
        }
        if (role.getId() == null) {
            roleDelegate.saveRole(role, menuIds);
        } else {
            roleDelegate.updateRole(role, menuIds);
        }
    }

    public Set<String> queryAdminRoleCodes(Integer id) {
        return roleDelegate.queryAdminRoleCodes(id);
    }

    @Override
    public void delete(Integer id) {
        roleDelegate.delete(id);
    }

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<Role, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
