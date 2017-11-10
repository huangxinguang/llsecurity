package com.laile.security.service.auth.admin.impl;

import com.laile.esf.common.util.MD5Util;
import com.laile.esf.common.util.Page;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.delegate.auth.admin.AdminDelegate;
import com.laile.security.core.dto.AdminDto;
import com.laile.security.core.exception.TipsException;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.util.CipherHelper;
import com.laile.security.core.util.ConfigConst;
import com.laile.security.core.vo.admin.AdminVo;
import com.laile.security.service.auth.admin.IAdminService;
import com.laile.security.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/9/26 下午3:14.
 */
@Service
public class AdminService extends AbstractService<Admin,Integer> implements IAdminService {

    @Autowired
    private AdminDelegate adminDelegate;

    @Override
    public Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey) {
        return adminDelegate.queryAdminListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(AdminDto adminDto) {
        if(adminDto.getId() == null) {
            Boolean exist = adminDelegate.checkNameExist(adminDto.getAdminName());
            if(exist) {
                throw new TipsException("亲，此用户名已经被使用了。");
            }
            adminDelegate.saveAdmin(adminDto);
        }else {
            adminDelegate.updateAdmin(adminDto);
        }
    }

    @Override
    public void delete(Integer id) {
        adminDelegate.deleteAdmin(id);
    }

    @Override
    public void changePwd(Integer id, String password, String newPassword) {
        Admin admin = adminDelegate.selectByPrimaryKey(id);
        if(!admin.getPassword().equalsIgnoreCase(CipherHelper.encryptPassword(password, ConfigConst.SOLT))) {
            throw new TipsException("亲，原密码不正确。");
        }else {
            Admin newAdmin = new Admin();
            newAdmin.setId(admin.getId());
            newAdmin.setPassword(MD5Util.MD5(newPassword));
            adminDelegate.updateByPrimaryKeySelective(admin);
        }
    }

    @Override
    public void unlock(Integer id,String password) {
        Admin admin = adminDelegate.selectByPrimaryKey(id);
        if(!admin.getPassword().equalsIgnoreCase(CipherHelper.encryptPassword(password, ConfigConst.SOLT))) {
            throw new TipsException("密码不正确！");
        }
    }

    @Override
    public Admin getAdmin(String name) {
        return adminDelegate.getAdmin(name);
    }

    @Override
    public Admin login(String name, String password) {
        return adminDelegate.login(name,password);
    }

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<Admin, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
