package com.searchitemsapp.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@Component
public class UserDto {

	private Long id;
	private String username;
	private String password;
	private Boolean enabled;
	private Boolean accountNonLocked;
	private Boolean accountNonExpired;
}
