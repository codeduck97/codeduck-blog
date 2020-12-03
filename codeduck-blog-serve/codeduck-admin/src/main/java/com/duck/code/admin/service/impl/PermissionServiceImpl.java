package com.duck.code.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duck.code.admin.mapper.AdminMapper;
import com.duck.code.admin.mapper.PermissionMapper;
import com.duck.code.admin.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duck.code.admin.utils.TreeUtil;
import com.duck.code.commons.entity.sys.Permission;
import com.duck.code.commons.entity.sys.Tree;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author Code Duck
 * @since 2020-11-27
 */
@Service
@Slf4j
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private AdminMapper adminMapper;

    @Override
    public List<Permission> findUserPermissions(String username) {
        return baseMapper.findUserPermissions(username);
    }

    @Override
    public Map<String, Object> getPermissionMenus() {
        Map<String, Object> result = new HashMap<>();
        try {
            QueryWrapper<Permission> wrapper = new QueryWrapper<>();
            wrapper.orderByDesc("id");
            List<Permission> menus = baseMapper.selectList(wrapper);

            List<Tree<Permission>> trees = new ArrayList<>();
            List<String> ids = new ArrayList<>();
            buildTrees(trees, menus, ids);

            result.put("ids", ids);
            Tree<Permission> menuTree = TreeUtil.build(trees);
            result.put("rows", menuTree);
        } catch (NumberFormatException e) {
            log.error("查询菜单失败", e);
            result.put("rows", null);
            result.put("total", 0);
        }

        return result;
    }

    private void buildTrees(List<Tree<Permission>> trees, List<Permission> menus, List<String> ids) {
        menus.forEach(menu -> {
            ids.add(menu.getId().toString());

            Tree<Permission> tree = new Tree<>();
            tree.setId(menu.getId().toString());
            tree.setValue(tree.getId());
            tree.setParentId(menu.getParentId().toString());
            tree.setLabel(menu.getMenuName());
            tree.setPermission(menu.getPermissionCode());
            trees.add(tree);
        });
    }


}
