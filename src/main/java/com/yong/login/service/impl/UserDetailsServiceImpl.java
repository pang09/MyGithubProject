package com.yong.login.service.impl;

import com.yong.login.entity.Admin;
import com.yong.login.entity.AdminLogin;
import com.yong.login.service.AdminService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    //获取数据库中的user相关数据，自行设计
    @Resource
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getAdminByUserName(username);
        String password=admin.getPassword();
//        if (StringUtils.isBlank(password)){
//            throw new UsernameNotFoundException("账号不存在！");
//        }
        AdminLogin adminLogin = new AdminLogin();
        adminLogin.setUsername(username);
        adminLogin.setPassword(password);
        return adminLogin;
    }

}
