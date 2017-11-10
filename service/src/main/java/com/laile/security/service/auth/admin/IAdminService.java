package com.laile.security.service.auth.admin;

import com.laile.esf.common.util.Page;
import com.laile.security.core.dto.AdminDto;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.vo.admin.AdminVo;
import com.laile.security.service.IBaseService;


/**
 * Created by huangxinguang on 2017/9/26 下午3:13.
 */
public interface IAdminService extends IBaseService<Admin,Integer> {

    Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey);

    void saveOrUpdate(AdminDto adminDto);

    void delete(Integer id);

    void changePwd(Integer id, String password, String newPassword);

    void unlock(Integer id, String password);

    Admin getAdmin(String name);

    Admin login(String name, String password);
}
