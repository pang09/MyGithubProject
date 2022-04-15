package com.yong.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.yong.login.common.lang.Result;
import com.yong.login.entity.Admin;
import com.yong.login.entity.Register;
import com.yong.login.entity.Role;
import com.yong.login.mapper.AdminMapper;
import com.yong.login.security.UserDetailServiceImpl;
import com.yong.login.service.AdminService;
import com.yong.login.service.RoleService;
import com.yong.login.util.JwtUtils;
import com.yong.login.util.MD5Util;
import com.yong.login.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private UserDetailServiceImpl userDetailService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AdminService adminService;

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private JwtUtils jwtUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result sendSms(String telephone, String smsCode) {
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
            return Result.succ("成功发送验证码");
        } else {
            return Result.fail("验证码发送失败");
        }
    }

    @Override
    public Result login(String username, String password) {
        Admin admin = adminService.getByUsername(username);
        if (admin == null) {
            return Result.fail("用户名不存在!");
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        String password1 = MD5Util.code(password);
        if (!passwordEncoder.matches(password1, userDetails.getPassword())) {
            return Result.fail("用户名或密码不正确!");
        }
        //生成令牌
        String token = jwtUtil.generateToken(username);
        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return Result.succ(tokenMap);
    }


    @Override
    public Admin getByUsername(String username) {
        return getOne(new QueryWrapper<Admin>().eq("username", username));
    }

    @Override
    public Result register(Register register) {
        String password = MD5Util.code(register.getPassword());
        Admin admin = new Admin();
        admin.setUsername(register.getUsername());
        admin.setPassword(password);
        admin.setSex(register.getSex());
        admin.setLocation(register.getLocation());
        admin.setTel(register.getTel());
        int insert = adminMapper.insert(admin);
        List<Admin> admins = adminMapper.selectList(null);
        admins.forEach(System.out::println);
        if (insert == 1) {
            return Result.succ("注册成功");
        }
        return Result.fail("注册失败");
    }

    @Override
    public Result userNameisExist(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username", username);
        Admin admin = adminMapper.selectOne(wrapper);
        if (admin == null) {
            return Result.succ(null);
        }
        return Result.fail("用户已注册");
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        Admin admin = adminMapper.selectById(userId);

        //  ROLE_admin,ROLE_normal,sys:user:list,....
        String authority = "";

        if (redisUtil.hasKey("GrantedAuthority:" + admin.getUsername())) {
            authority = (String) redisUtil.get("GrantedAuthority:" + admin.getUsername());

        } else {
            // 获取角色编码
            List<Role> roles = roleService.list(new QueryWrapper<Role>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }
            redisUtil.set("GrantedAuthority:" + admin.getUsername(), authority, 60 * 60);
        }
        return authority;
    }
    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

        List<Admin> sysUsers = this.list(new QueryWrapper<Admin>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));

        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });

    }
}
