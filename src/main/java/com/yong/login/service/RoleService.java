package com.yong.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yong.login.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<Role> listRolesByUserId(Long userId);

}
