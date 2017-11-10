package com.laile.security.core.dto;


import com.laile.security.core.model.auth.admin.Admin;

/**
 * Created by huangxinguang on 2017/10/14 上午10:24.
 */
public class AdminDto extends Admin {

    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
