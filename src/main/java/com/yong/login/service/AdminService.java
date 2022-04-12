package com.yong.login.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yong.login.common.lang.Result;
import com.yong.login.entity.Admin;
import com.yong.login.entity.Register;

public interface AdminService extends IService<Admin> {

    Result sendSms(String telephone, String smsCode);

    Result login(String username, String password);

    Admin getByUsername(String username);

    Result register(Register register);

    Result userNameisExist(String username);
}
