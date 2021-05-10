package com.searchitemsapp.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesDto {
	private Long id;
	private String roleName;
	private LocalDateTime createAt;
    private List<UserDto> users;
}
