package com.searchitemsapp.service;

import java.util.List;

public interface JwtService {

	abstract String crearToken(String user, List<String> roles);
	
	abstract boolean isBearer(String authorization);
	
	abstract String user(String authorization);
	
	abstract List<String> roles(String authorization);
}
