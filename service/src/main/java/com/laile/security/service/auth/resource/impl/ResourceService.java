package com.laile.security.service.auth.resource.impl;

import com.laile.esf.common.util.Page;
import com.laile.security.core.delegate.AbstractDelegate;
import com.laile.security.core.delegate.auth.resource.ResourceDelegate;
import com.laile.security.core.delegate.auth.role.RoleResourceDelegate;
import com.laile.security.core.model.auth.resource.Resource;
import com.laile.security.core.model.auth.role.RoleResource;
import com.laile.security.core.plugin.SqlCondition;
import com.laile.security.core.vo.resource.MenuVo;
import com.laile.security.core.vo.role.TreeNode;
import com.laile.security.service.auth.resource.IResourceService;
import com.laile.security.service.impl.AbstractService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by huangxinguang on 2017/9/26 下午3:20.
 */
@Component
public class ResourceService extends AbstractService<Resource,Integer> implements IResourceService {

    @Autowired
    private ResourceDelegate resourceDelegate;

    @Autowired
    private RoleResourceDelegate roleResourceDelegate;

    @Autowired
    @Override
    public void setBaseDelegate(AbstractDelegate<Resource, Integer> baseDelegate) {
        super.setBaseDelegate(baseDelegate);
    }

    public List<MenuVo> getMenuTree(Integer id) {
        List<Resource> allMenuList = resourceDelegate.queryResource(id);
        if(CollectionUtils.isEmpty(allMenuList)) {
            return null;
        }
        List<MenuVo> menuList = new ArrayList<>();
        MenuVo menu;
        for(Resource item : allMenuList) {
            menu = new MenuVo();
            BeanUtils.copyProperties(item,menu);
            menuList.add(menu);
        }

        List<MenuVo> menuTreeList = new ArrayList<>();
        //加载一级菜单
        MenuVo menuDto;
        for (Resource item : allMenuList) {
            menuDto = new MenuVo();
            BeanUtils.copyProperties(item,menuDto);
            if (menuDto.getParentId().intValue() == -1) {
                menuTreeList.add(menuDto);
            }
        }
        //递归加载孩子所有菜单
        for (MenuVo currentMenu : menuTreeList) {
            currentMenu.setChilds(getChild(currentMenu.getId(),menuList));
        }
        return menuTreeList;
    }

    public List<TreeNode> getRoleTree() {
        //加载所有节点
        List<Resource> resourceList = resourceDelegate.getAllMenuList();
        //所有节点
        List<TreeNode> allNodeList = new ArrayList<>();

        TreeNode node;
        for(Resource resource : resourceList) {
            node = new TreeNode();
            node.setId(resource.getId());
            node.setName(resource.getName());
            node.setChecked(false);
            node.setOpen(true);
            node.setpId(resource.getParentId().intValue() == -1 ? 0 : resource.getParentId());
            allNodeList.add(node);
        }
        return allNodeList;
    }

    @Override
    public List<TreeNode> getSelectedRoleTree(Integer id) {
        //加载所有节点
        List<Resource> resourceList = resourceDelegate.getAllMenuList();
        //所有节点
        List<TreeNode> allNodeList = new ArrayList<>();

        SqlCondition condition = new SqlCondition();
        condition.put(" where role_id=",id);
        List<RoleResource> roleResourceList = roleResourceDelegate.selectByCondition(condition);

        TreeNode node;
        for(Resource resource : resourceList) {
            node = new TreeNode();
            node.setId(resource.getId());
            node.setName(resource.getName());
            node.setOpen(true);
            node.setpId(resource.getParentId().intValue() == -1 ? 0 : resource.getParentId());

            for(RoleResource roleResource : roleResourceList) {
                if(roleResource.getResourceId().intValue() == resource.getId().intValue()) {
                    node.setChecked(true);
                    break;
                }
            }
            allNodeList.add(node);
        }

        return allNodeList;
    }

    @Override
    public List<TreeNode> getSelectedResourceTree(Integer resourceId) {
        Resource resource = resourceDelegate.selectByPrimaryKey(resourceId);
        //加载所有节点
        List<Resource> resourceList = resourceDelegate.getAllMenuList();
        //所有节点
        List<TreeNode> allNodeList = new ArrayList<>();

        TreeNode treeNode;
        for(Resource item : resourceList) {

            treeNode = new TreeNode();
            treeNode.setId(item.getId());
            treeNode.setName(item.getName());
            if(item.getCode().equals(resource.getCode())) {
                treeNode.setChecked(true);
            }else {
                treeNode.setChecked(false);
            }
            treeNode.setOpen(true);
            treeNode.setpId(item.getParentId().intValue() == -1 ? 0 : item.getParentId());
            allNodeList.add(treeNode);
        }
        return allNodeList;
    }

    @Override
    public Set<String> queryResourceCodes(Integer id) {
        return resourceDelegate.queryResourceCodes(id);
    }

    @Override
    public Page<Resource> queryResourceListPage(Integer currentPage, Integer showCount, String searchKey) {
        return resourceDelegate.queryResourceListPage(currentPage,showCount,searchKey);
    }

    @Override
    public void saveOrUpdate(Resource resource) {
        if (resource.getId() == null) {
            resourceDelegate.insertSelective(resource);
        } else {
            resourceDelegate.updateByPrimaryKeySelective(resource);
        }
    }


    private List<MenuVo> getChild(Integer menuId, List<MenuVo> menuList) {
        // menuId猜的的 子菜单
        List<MenuVo> childList = new ArrayList<>();
        for (MenuVo menu : menuList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId().equals(menuId)) {
                childList.add(menu);
            }
        }

        // 把子菜单的子菜单再循环一遍
        for (MenuVo menu : childList) {// 没有url子菜单还有子菜单
            menu.setChilds(getChild(menu.getId(), menuList));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
}
