package com.example.demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.service.userService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtFilter extends OncePerRequestFilter
{
	@Autowired
	private jwtService jwts;

	@Autowired
	private userService uService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String userName = null;
		if(authHeader != null && authHeader.startsWith("Bearer"))
		{
			token = authHeader.substring(7);
			userName = jwts.extractUserNAme(token);
		}
		
		if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userdetails = uService.loadUserByUsername(userName);
			if(jwts.validateToken(token, userdetails))
			{
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}

}
