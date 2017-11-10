package com.laile.security.core.dao.auth.resource;

import com.laile.security.core.dao.IBaseDAO;
import com.laile.security.core.model.auth.resource.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午2:41.
 */
@Repository
public interface ResourceDAO extends IBaseDAO<Resource,Integer> {

    Set<String> queryResourceCodes(Integer id);

    /**
     * 通过用户ID查询
     * @param id
     * @return
     */
    List<Resource> queryResource(Integer id);
}
