package com.laile.security.core.dao.auth.role;

import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.model.auth.role.Role;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午2:40.
 */
@Repository
public interface RoleDAO extends IBaseDAO<Role,Integer> {
    /**
     * 查询管理角色编码
     * @param id
     * @return
     */
    Set<String> queryAdminRoleCodes(Integer id);
}
