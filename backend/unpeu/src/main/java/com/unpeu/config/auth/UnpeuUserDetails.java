package com.unpeu.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.unpeu.domain.entity.User;


public class UnpeuUserDetails implements UserDetails {
	@Autowired
	User user;
	boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialNonExpired;
    boolean enabled = false;
    List<GrantedAuthority> roles = new ArrayList<>();
    
    public UnpeuUserDetails(User user) {
    		super();
    		this.user = user;
    }

	public void setUser(User user){
		this.user = user;
	}
    public User getUser() {
    		return this.user;
    }
	@Override
	public String getPassword() {
		return this.user.getSocialDomain();
	}
	@Override
	public String getUsername() {
		return this.user.getUserLogin();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}
	public void setAuthorities(List<GrantedAuthority> roles) {
		this.roles = roles;
	}
}
