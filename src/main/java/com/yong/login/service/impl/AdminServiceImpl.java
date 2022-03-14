package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.yong.login.entity.Admin;
import com.yong.login.entity.R;
import com.yong.login.entity.Register;
import com.yong.login.mapper.AdminMapper;
import com.yong.login.service.AdminService;
import com.yong.login.util.JwtTokenUtil;
import com.yong.login.util.MD5Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AdminService adminService;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public R sendSms(String telephone, String smsCode) {
        //1.调用工具类发送短信
        // String result = SmsUtils.send(telephone, "旅游", "SMS_195723031", smsCode);
        //模拟每次发送成功
        String result = "OK";
        //2.判断返回的字符串是否是OK，如果是OK表示发送成功
        if ("OK".equals(result)) {
            //获取操作string类型的对象
            ValueOperations<String, Object> ops = redisTemplate.opsForValue();
            //将验证码保存到redis中，并且设置过期的时间为30秒
            ops.set("smsCode_" + telephone, smsCode, 60, TimeUnit.SECONDS);
            return R.ok().message("成功发送验证码");
        } else {
            return R.error().message("验证码发送失败");
        }
    }

    @Override
    public R login(String username, String password) {
        Admin admin = adminService.getAdminByUserName(username);
        if (admin==null){
            return R.error().message("用户名不存在!");
        }
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        String password1=MD5Util.code(password);
        if (!passwordEncoder.matches(password1, userDetails.getPassword())) {
            return R.error().message("用户名或密码不正确!");
        }
        //生成令牌
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return R.ok().data("token",tokenMap);
    }


    @Override
    public Admin getAdminByUserName(String username) {
        return baseMapper.selectOne(new QueryWrapper<Admin>().eq("username", username)
//                .eq("enabled", true)
        );
    }

    @Override
    public R register(Register register) {
        String password=MD5Util.code(register.getPassword());
        Admin admin=new Admin();
        admin.setUsername(register.getUsername());
        admin.setPassword(password);
        admin.setSex(register.getSex());
        admin.setLocation(register.getLocation());
        admin.setTel(register.getTel());
        admin.setCreateTime(new Date());
        admin.setUpdateTime(new Date());
        int insert=adminMapper.insert(admin);
        List<Admin> admins = adminMapper.selectList(null);
        admins.forEach(System.out::println);
        if (insert==1) {
            return R.ok().message("注册成功");
        }
        return R.error().message("注册失败");
    }

    @Override
    public R userNameisExist(String username) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("username",username);
        Admin admin=adminMapper.selectOne(wrapper);
        if (admin==null){
            return R.ok();
        }
        return R.error().message("用户已注册");
    }
}
