package com.laile.security.service.auth.resource.impl;


import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.model.auth.role.RoleResource;
import com.laile.security.service.auth.resource.IRoleResourceService;
import com.laile.security.service.impl.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangxinguang on 2017/9/26 下午3:18.
 */
@Service
public class RoleResourceService extends AbstractService<RoleResource,Integer> implements IRoleResourceService {

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<RoleResource, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }
}
