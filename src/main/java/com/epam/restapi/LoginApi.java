package com.epam.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.UserDto;
import com.epam.service.UserService;

import jakarta.validation.Valid;

@RestController
public class LoginApi {
	@Autowired
	private UserService loginService;
	
	@PostMapping("users")
	public ResponseEntity<UserDto> login(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<>(loginService.signIn(userDto),HttpStatus.OK);		
	}
	@PostMapping("saveUsers")
	public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<>(loginService.signUp(userDto),HttpStatus.OK);		
	}
}

