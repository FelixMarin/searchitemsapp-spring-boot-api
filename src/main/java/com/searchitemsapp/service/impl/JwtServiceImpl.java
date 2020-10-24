package com.searchitemsapp.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.searchitemsapp.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService {
	
	public static final String BEARER = "Bearer ";

	private static final String USER = "user";
	private static final String ROLES = "roles";
	private static final String ISSUER = "sia-authority";
	private static final int EXPRES_IN_MILLISECOND = 3600000;
	private static final String SECRET = "clave-sia";
	
	public String crearToken(String user, List<String> roles) {
		
		return JWT.create().withIssuer(ISSUER)
				.withIssuedAt(new Date()).withNotBefore(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPRES_IN_MILLISECOND))
				.withClaim(USER, user)
				.withArrayClaim(ROLES, roles.toArray(new String[0]))
				.sign(Algorithm.HMAC256(SECRET));
	}
	
	public boolean isBearer(String authorization) {
		return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
	} 
	
	public String user(String authorization) {
		return this.verify(authorization).getClaim(USER).asString();
	}
	
	private DecodedJWT verify(String authorization) {
		
		DecodedJWT decoded = null;
	
		if(!this.isBearer(authorization)) {
			throw new TokenExpiredException("It is not Bearer");
		}
	
		decoded = JWT.require(Algorithm.HMAC256(SECRET))
				.withIssuer(ISSUER).build().verify(authorization.substring(BEARER.length()));
		
		return decoded;
	}
	
	public List<String> roles(String authorization) {
		return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
	}
}
