package com.searchitemsapp.config.filter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.searchitemsapp.service.impl.JwtServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	
	private static final String AUTHORIZATION = "Authorization";

	@Autowired
	public JwtServiceImpl jwtService;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws IOException, ServletException {
		
		String authHeader = request.getHeader(AUTHORIZATION);
		
		if(jwtService.isBearer(authHeader)) {
			log.debug(">>> FILTER JWT...");
			List<GrantedAuthority> authorities = jwtService.roles(authHeader).stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(jwtService.user(authHeader), null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
}
