package com.rongdu.loans.service.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.rongdu.loans.service.account.VUserManager;

@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private VUserManager vUserManager;

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {
		UserDetails user = vUserManager.loadUserByUsername(userName);
		if (user == null){
			throw new UsernameNotFoundException("用户不存在");	
		}
		if (user.getAuthorities().isEmpty()) {
			throw new UsernameNotFoundException("尚未给该用户分配后台管理权限");	
		}
		return user;
	}
	
}
