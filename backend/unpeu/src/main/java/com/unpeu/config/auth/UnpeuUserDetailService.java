package com.unpeu.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.unpeu.domain.entity.User;
import com.unpeu.service.iface.IUserService;



@Component
public class UnpeuUserDetailService implements UserDetailsService{
//	@Autowired
//	UserService userService;

	private final IUserService userService;
	@Autowired
	public UnpeuUserDetailService(IUserService userService) {
		this.userService = userService;
	}

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {
    		User user = userService.getUserByUserLogin(userLogin);
    		if(user != null) {
    			UnpeuUserDetails userDetails = new UnpeuUserDetails(user);
    			return userDetails;
    		}
    		return null;
    }
}
