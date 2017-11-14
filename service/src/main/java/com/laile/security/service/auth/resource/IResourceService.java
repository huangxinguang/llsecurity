package com.laile.security.service.auth.resource;


import com.laile.esf.common.util.Page;
import com.laile.security.core.model.auth.resource.Resource;
import com.laile.security.core.vo.resource.MenuVo;
import com.laile.security.core.vo.role.TreeNode;
import com.laile.security.service.IBaseService;

import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:19.
 */
public interface IResourceService extends IBaseService<Resource,Integer> {
    /**
     * 获取用户所有菜单树
     * @return
     */
    List<MenuVo> getMenuTree(Integer id);

    /**
     * 角色树
     * @return
     */
    List<TreeNode> getRoleTree();

    /**
     * 已经选中的树状态
     * @param id
     * @return
     */
    List<TreeNode> getSelectedRoleTree(Integer id);

    /**
     * 获取选取的资源树
     * @return
     */
    List<TreeNode> getSelectedResourceTree(Integer id);

    /**
     * 查询用户拥有的资源编码
     * @param id
     * @return
     */
    Set<String> queryResourceCodes(Integer id);

    /**
     * 查询分页
     * @param currentPage
     * @param showCount
     * @param searchKey
     * @return
     */
    Page<Resource> queryResourceListPage(Integer currentPage,Integer showCount,String searchKey);

    /**
     * 保存或更新
     * @param resource
     */
    void saveOrUpdate(Resource resource);
}
