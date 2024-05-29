package com.example.demo.config;

import java.security.Key;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class jwtService 
{	
	private static final String SECERET = "VGhpcyBpcyBzZWN1cml0eSBjb2Rlsdcsdcdscdscsdcdscsdcsdcsdcdscsdcsdcdscsd";

	public String generateToken(String userName)
	{
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() 
	{
		byte[] keybytes = Decoders.BASE64.decode(SECERET);
		return Keys.hmacShaKeyFor(keybytes);
	}
	
	public Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}
	public String extractUserNAme(String token)
	{
		return extractClaim(token, Claims::getSubject);
	}
	private <T> T extractClaim(String token, Function<Claims, T> tokenResolve) 
	{
		final Claims clms = extractAllClaims(token);
		return tokenResolve.apply(clms);
	}

	private Claims extractAllClaims(String token)
	{
		return Jwts.parserBuilder().setSigningKey(getSignKey())
				.build().parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpire(String token)
	{
		return extractExpiration(token).before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails details)
	{
		final String username = extractUserNAme(token);
		return (username.equals(details.getUsername()) && !isTokenExpire(token));
	}
}
