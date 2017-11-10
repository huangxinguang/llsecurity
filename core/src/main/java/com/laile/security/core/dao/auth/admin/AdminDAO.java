package com.laile.security.core.dao.auth.admin;

import com.laile.esf.common.util.Page;
import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.vo.admin.AdminVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDAO extends IBaseDAO<Admin,Integer> {

    List<AdminVo> queryAdminListPage(@Param("page") Page<AdminVo> page, @Param("searchKey") String searchKey);

    Admin login(@Param("name") String name, @Param("password") String password);

    void addLoginErrorTime(@Param("name") String name, @Param("time") Integer time);

    void resetLoginErrorTime(@Param("name") String name);
}