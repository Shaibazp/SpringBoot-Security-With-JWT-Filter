package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.userInfo;
import com.example.demo.entity.userinfoDetails;
import com.example.demo.repository.userInfoRepository;

@Service
public class userService implements UserDetailsService 
{
	@Autowired
	private userInfoRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		Optional<userInfo> byname = repository.findByName(username);
		return byname.map(userinfoDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
	}
	
	public userInfo addUser(userInfo info)
	{
		info.setPasswords(encoder.encode(info.getPasswords()));
		
		return repository.save(info);
	}
	
	public List<userInfo> getAlluser()
	{
		return repository.findAll();
	}
	
	public userInfo getUSer(Long id)
	{
		return repository.findById(id).orElseThrow(()-> new RuntimeException("Not Found.....!"));
	}

}
