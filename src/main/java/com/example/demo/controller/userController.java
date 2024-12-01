package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.jwtService;
import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.userInfo;
import com.example.demo.service.userService;


@RestController
@RequestMapping("/auths")
public class userController 
{
	@Autowired
	private userService services;
	
	@Autowired
	private AuthenticationManager auth;
	
	@Autowired
	private jwtService jwts;
	
		@GetMapping("/welc")
		public String wel()
		{
			return "Connected.....";
		}
		
		@PostMapping("/add")
		public userInfo addUSer(@RequestBody userInfo info)
		{
			return services.addUser(info);
		}
		
		@PostMapping("/logins")
		public String userLogin(@RequestBody AuthRequest infos)
		{
			Authentication authentic = auth.authenticate(new UsernamePasswordAuthenticationToken(infos.getUserName(), infos.getPassword()));
			if(authentic.isAuthenticated())
			{
				return jwts.generateToken(infos.getUserName());
				//store in database tocken with Flag -----Login- Y,   Logout-N
			}
			else
			{
				throw new UsernameNotFoundException("Not Found");
			}
		}
		
		@GetMapping("/getall")
		public List<userInfo> getall()
		{
			return services.getAlluser();
		}
		
		@GetMapping("/getalls/{id}")
		public userInfo getalls(@PathVariable Long id)
		{
			return services.getUSer(id);
		}
		
}
