package com.example.demo.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class userinfoDetails implements UserDetails
{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userName = null;
	 String password = null;
	List<GrantedAuthority> author;

	public userinfoDetails(userInfo info)
	{
		userName = info.getName();
		password = info.getPasswords();
		author = java.util.Arrays.stream(info.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return author;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
