package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yong.login.entity.Role;
import com.yong.login.mapper.RoleMapper;
import com.yong.login.service.RoleService;

import java.util.List;

public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> listRolesByUserId(Long userId) {

        List<Role> sysRoles = this.list(new QueryWrapper<Role>()
                .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

        return sysRoles;
    }
}
