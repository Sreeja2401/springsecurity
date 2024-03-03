package com.epam.service;

import com.epam.dto.UserDto;


public interface UserService {

	public UserDto signUp(UserDto adminAndUserDto);
	public UserDto signIn(UserDto adminAndUserDto);
}
