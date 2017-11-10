package com.laile.security.core.delegate.auth.role;

import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.model.auth.role.RoleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by huangxinguang on 2017/9/26 下午3:06.
 */
@Transactional
@Component
public class RoleResourceDelegate extends AbstractDelegate<RoleResource,Integer> {

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<RoleResource, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }
}
