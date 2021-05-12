package com.searchitemsapp.config.filter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TimeAccessFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug(">>>>> TimeAccessFilter... Open: 00:00 to 23:00");
		
		var hour = LocalDateTime.now().getHour();
		
		if(0 >= hour && hour < 23) {
			filterChain.doFilter(request, response);
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
}
