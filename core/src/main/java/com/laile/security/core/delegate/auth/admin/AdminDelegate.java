package com.laile.security.core.delegate.auth.admin;

import com.laile.esf.common.util.DateUtil;
import com.laile.esf.common.util.Page;
import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.dao.auth.admin.AdminDAO;
import com.laile.security.core.dao.auth.admin.AdminRoleDAO;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.dto.AdminDto;
import com.laile.security.core.exception.TipsException;
import com.laile.security.core.model.auth.admin.Admin;
import com.laile.security.core.model.auth.admin.AdminRole;
import com.laile.security.core.plugin.SqlCondition;
import com.laile.security.core.vo.admin.AdminVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangxinguang on 2017/9/26 下午3:05.
 */
@Transactional
@Component
public class AdminDelegate extends AbstractDelegate<Admin,Integer> {

    @Autowired
    private AdminDAO adminDAO;

    @Autowired
    private AdminRoleDAO adminRoleDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<Admin, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }

    public Page<AdminVo> queryAdminListPage(Integer currentPage, Integer showCount, String searchKey) {
        if(currentPage == null) {
            currentPage = 1;
        }
        Page<AdminVo> page = new Page<>(currentPage,showCount);
        List<AdminVo> list = adminDAO.queryAdminListPage(page,searchKey);
        page.setResultList(list);
        return page;
    }

    public void saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto,admin);
        adminDAO.insert(admin);

        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setCreateBy(adminDto.getCreateBy());
        adminRole.setCreateTime(adminDto.getCreateTime());
        adminRole.setRoleId(adminDto.getRoleId());
        adminRole.setUpdateBy(adminDto.getCreateBy());
        adminRole.setUpdateTime(adminDto.getCreateTime());
        adminRoleDAO.insert(adminRole);
    }

    public void updateAdmin(AdminDto adminDto) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminDto,admin);
        adminDAO.updateByPrimaryKeySelective(adminDto);

        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(admin.getId());
        adminRole.setRoleId(adminDto.getRoleId());

        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_id=",adminDto.getId());
        adminRoleDAO.updateByParam(adminRole,condition);
    }

    public void deleteAdmin(Integer id) {
        adminDAO.deleteByPrimaryKey(id);

        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_id=",id);
        adminRoleDAO.deleteByParam(condition);
    }

    public Admin getAdmin(String name) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_name=",name);
        List<Admin> adminList = adminDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(adminList)) {
            return adminList.get(0);
        }
        return null;
    }

    public Boolean checkNameExist(String name) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where admin_name=",name);
        List<Admin> adminList = adminDAO.selectByCondition(condition);
        if(!CollectionUtils.isEmpty(adminList)) {
            return true;
        }
        return false;
    }

    public Admin login(String name,String password) {
        Admin admin = adminDAO.login(name, password);
        if (admin == null) {
            throw new TipsException("用户名或密码错误");
        }
        if (!StringUtils.equals(password, admin.getPassword())) {
            if (DateUtil.isSameDay(DateUtil.parse(DateUtil.format(admin.getLastLoginTime(), DateUtil.YYYY_MM_DD)),
                    DateUtil.fomatDate(DateUtil.getDay()))) {
                try {
                    admin.setLoginErrorCount(admin.getLoginErrorCount() + 1);
                    adminDAO.addLoginErrorTime(admin.getAdminName(), 1);
                } catch (Exception e) {
                    logger.error("update login err Time error", e);
                }
            } else {
                adminDAO.addLoginErrorTime(admin.getAdminName(), admin.getLoginErrorCount() * -1 + 1);
                admin.setLoginErrorCount(1);
            }
            throw new TipsException("用户名/密码错误,当天可尝试登录剩余次数[" + (admin.getLimitCount() - admin.getLoginErrorCount()) + "]次");
        } else {
            if (admin.getLoginErrorCount() > 0) {
                adminDAO.addLoginErrorTime(admin.getAdminName(), admin.getLoginErrorCount() * -1);
            }
        }
        return admin;
    }
}
