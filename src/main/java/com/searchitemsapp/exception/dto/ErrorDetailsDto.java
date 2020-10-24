package com.searchitemsapp.exception.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsDto {
    private LocalDateTime timestamp;
    private String message;
    private String details;
    private int status;
}
