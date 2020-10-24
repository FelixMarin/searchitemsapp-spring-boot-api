package com.searchitemsapp.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.searchitemsapp.entities.TbSiaRoles;

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
	private String email;
	private Boolean enabled;
	private Boolean accountNonLocked;
	private Boolean accountNonExpired;
	private List<TbSiaRoles> roles;
	private LocalDateTime createAt;
}
