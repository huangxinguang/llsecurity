package com.laile.security.core.delegate.auth.role;

import com.laile.esf.common.util.Page;
import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.dao.auth.admin.AdminRoleDAO;
import com.laile.security.core.dao.auth.role.RoleDAO;
import com.laile.security.core.dao.auth.role.RoleResourceDAO;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.model.auth.admin.AdminRole;
import com.laile.security.core.model.auth.role.Role;
import com.laile.security.core.model.auth.role.RoleResource;
import com.laile.security.core.plugin.SqlCondition;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:03.
 */
@Transactional
@Component
public class RoleDelegate extends AbstractDelegate<Role,Integer> {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private AdminRoleDAO adminRoleDAO;

    @Autowired
    private RoleResourceDAO roleResourceDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<Role, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<Role> queryRoleListPage(Integer currentPage, Integer showCount, String searchKey) {
        if(currentPage == null) {
            currentPage = 1;
        }
        SqlCondition condition = new SqlCondition();
        if(!StringUtils.isEmpty(searchKey)) {
            condition.put(" where role_name like ","%"+searchKey+"%");
        }
        Page<Role> page = new Page<>(currentPage,showCount);
        roleDAO.selectPagination(page,condition);
        return page;
    }

    public Set<String> queryAdminRoleCodes(Integer id) {
        if(id.intValue() == 1) {//超级管理员
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(id);
            adminRole = adminRoleDAO.selectOne(adminRole);
            Role role = roleDAO.selectByPrimaryKey(adminRole.getRoleId());
            Set<String> superManagerCodeSet = new HashSet<>();
            superManagerCodeSet.add(role.getRoleCode());
            return superManagerCodeSet;
        }
        return roleDAO.queryAdminRoleCodes(id);
    }

    public Boolean querRoleCodeExist(String code) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where role_code=",code);
        long count = roleDAO.countByParam(condition);
        if(count > 0) {
            return true;
        }
        return false;
    }

    public void saveRole(Role role,Integer[] menuIds) {
        roleDAO.insert(role);
        insertRelation(role,menuIds);
    }

    public void updateRole(Role role,Integer[] menuIds) {
        roleDAO.updateByPrimaryKeySelective(role);

        //删除关联关系
        SqlCondition condition = new SqlCondition();
        condition.put(" where role_id=",role.getId());
        roleResourceDAO.deleteByParam(condition);

        //添加关联关系
        insertRelation(role,menuIds);
    }

    private void insertRelation(Role role,Integer[] menuIds) {
        if(!ArrayUtils.isEmpty(menuIds)) {
            List<RoleResource> roleResourceList = new ArrayList<>();
            RoleResource roleResource;
            for(Integer id : menuIds) {
                roleResource = new RoleResource();
                roleResource.setCreateBy(role.getCreateBy());
                roleResource.setCreateTime(role.getCreateTime());
                roleResource.setResourceId(id);
                roleResource.setStatus(1);
                roleResource.setRoleId(role.getId());
                roleResourceList.add(roleResource);
            }
            roleResourceDAO.insertBatch(roleResourceList);
        }
    }

    public void delete(Integer id) {
        roleDAO.deleteByPrimaryKey(id);
        //删除关联关系
        SqlCondition condition = new SqlCondition();
        condition.put(" where role_id=",id);
        roleResourceDAO.deleteByParam(condition);
    }
}
