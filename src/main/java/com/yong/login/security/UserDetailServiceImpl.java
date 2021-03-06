package com.yong.login.security;

import com.yong.login.entity.Admin;
import com.yong.login.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	AdminService adminService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = adminService.getByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("用户名或密码不正确");
		}
		return new AccountUser(admin.getId(), admin.getUsername(), admin.getPassword(), getUserAuthority(admin.getId()));
	}

	/**
	 * 获取用户权限信息（角色、菜单权限）
	 * @param userId
	 * @return
	 */
	public List<GrantedAuthority> getUserAuthority(Long userId){

		// 角色(ROLE_admin)、菜单操作权限 sys:user:list
		String authority = adminService.getUserAuthorityInfo(userId);  // ROLE_admin,ROLE_normal,sys:user:list,....

		//commaSeparatedStringToAuthorityList加名为id的权限
		//AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal")
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
}
}
