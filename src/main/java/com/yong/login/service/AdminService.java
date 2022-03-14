package com.yong.login.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yong.login.entity.Admin;
import com.yong.login.entity.R;
import com.yong.login.entity.Register;

public interface AdminService extends IService<Admin> {

    R sendSms(String telephone, String smsCode);

    R login(String username, String password);

    Admin getAdminByUserName(String username);

    R register(Register register);

    R userNameisExist(String username);
}
