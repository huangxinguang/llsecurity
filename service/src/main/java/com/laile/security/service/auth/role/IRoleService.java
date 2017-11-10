package com.laile.security.service.auth.role;

import com.laile.esf.common.util.Page;
import com.laile.security.core.model.auth.role.Role;
import com.laile.security.service.IBaseService;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:15.
 */
public interface IRoleService extends IBaseService<Role,Integer> {

    Page<Role> queryRoleListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(Role role, Integer[] menuIds);

    void delete(Integer id);

    Set<String> queryAdminRoleCodes(Integer id);
}
