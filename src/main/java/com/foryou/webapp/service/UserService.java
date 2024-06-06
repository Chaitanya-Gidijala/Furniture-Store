package com.foryou.webapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.foryou.webapp.entity.User;
import com.foryou.webapp.user.WebUser;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);

}
