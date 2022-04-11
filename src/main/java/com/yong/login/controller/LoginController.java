package com.yong.login.controller;

import com.yong.login.common.lang.Result;
import com.yong.login.entity.AdminLogin;
import com.yong.login.entity.Register;
import com.yong.login.service.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminService adminService;

    /**
     * 发送短信
     */
    @ApiOperation(value = "发送短信")
    @GetMapping("/sendSms")
    public Result sendSms(String telephone) {
        //1.调用工具类随机生成6个数字
        String smsCode = RandomStringUtils.randomNumeric(6);
        log.info("验证码：" + smsCode);
        //2.调用业务层发送短信
        Result resultInfo = adminService.sendSms(telephone, smsCode);
        //3.返回发送的结果
        return resultInfo;
    }


    @ApiOperation(value = "登录之后返回Token")
    @GetMapping("/login")
    public Result login(AdminLogin adminLogin){
        return adminService.login(adminLogin.getUsername(),adminLogin.getPassword());
    }

    @ApiOperation(value = "用户名是否存在")
    @GetMapping("/userNameisExist")
    public Result userNameisExist(String username){
        return adminService.userNameisExist(username);
    }

    @ApiOperation(value = "注册")
    @GetMapping("/register")
    public Result reg(Register register){
        return adminService.register(register);
    }
}
