package com.epam.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	int id;
	@Pattern(regexp = "^(?i)(admin|user)$",message="User type should be either admin or user")
	String userType;
	String userName;
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=*!])(?=\\S+$).{8,}$",
	message="At least 8 characters long\nContains at least one uppercase letter\nContains at least one lowercase letter\nContains at least one digit\nContains at least one special character (e.g. !, @, #, $, %, ^, &, *)")
	String password;
	
	
}
