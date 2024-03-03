package com.epam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.UserException;
import com.epam.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository adminAndUserRepo;
	@Autowired
	ModelMapper mapper;

	Logger logger = LogManager.getLogger(UserServiceImpl.class);

	public UserDto signUp(UserDto adminAndUserDto) throws UserException {
		logger.info("inside the signUp method");
		User user = mapper.map(adminAndUserDto, User.class);
		if (adminAndUserRepo.existsByuserName(user.getUsername())) {
			logger.warn("given user name already exist : {} ", user.getUsername());
			throw new UserException("given user name already exist");
		} else {
			User savedUser = adminAndUserRepo.save(user);
			logger.info("user signUp successfull with user name : {} ", savedUser.getUsername());
			return mapper.map(savedUser, UserDto.class);
		}
	}

	public UserDto signIn(UserDto adminAndUserDto) throws UserException {
		logger.info("inside the signIn method");
		return adminAndUserRepo.findByuserNameAndPassword(adminAndUserDto.getUserName(), adminAndUserDto.getPassword())
				.map(user -> {
					logger.info("user signIn successfull with user name : {} ", user.getUsername());
					return mapper.map(user, UserDto.class);
				}).orElseThrow(() -> {
					logger.warn("user authentication failed");
					return new UserException("Login unSuccessfull");
				});

	}
}
