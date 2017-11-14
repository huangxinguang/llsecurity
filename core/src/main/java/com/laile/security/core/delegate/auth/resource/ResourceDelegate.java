package com.laile.security.core.delegate.auth.resource;

import com.laile.esf.common.util.Page;
import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.dao.auth.resource.ResourceDAO;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.model.auth.resource.Resource;
import com.laile.security.core.plugin.SqlCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:06.
 */
@Transactional
@Component
public class ResourceDelegate extends AbstractDelegate<Resource,Integer> {

    @Autowired
    private ResourceDAO resourceDAO;

    @Autowired
    @Override
    public void setBaseMapper(IBaseDAO<Resource, Integer> baseMapper) {
        super.setBaseMapper(baseMapper);
    }


    public List<Resource> getMenuList(Integer parentId) {
        SqlCondition condition = new SqlCondition();
        condition.put(" where type=1 and parent_id=",parentId);
        List<Resource> resourceList = resourceDAO.selectByCondition(condition);
        return resourceList;
    }

    public List<Resource> getAllMenuList() {
        SqlCondition condition = new SqlCondition();
        condition.put(" where type=",1);
        List<Resource> resourceList = resourceDAO.selectByCondition(condition);
        return resourceList;
    }

    public Set<String> queryResourceCodes(Integer id) {
        return resourceDAO.queryResourceCodes(id);
    }

    public List<Resource> queryResource(Integer id) {
        return resourceDAO.queryResource(id);
    }

    public Page<Resource> queryResourceListPage(Integer currentPage,Integer showCount,String searchKey) {
        if(currentPage == null) {
            currentPage = 1;
        }
        Page<Resource> page = new Page<>(currentPage,showCount);
        SqlCondition condition = new SqlCondition();
        if(!StringUtils.isEmpty(searchKey)) {
            condition.put(" where name like ","'%"+searchKey+"%'");
        }
        resourceDAO.selectPagination(page,condition);
        return page;
    }

}
