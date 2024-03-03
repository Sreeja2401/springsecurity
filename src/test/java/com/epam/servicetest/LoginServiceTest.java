package com.epam.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exceptions.UserException;
import com.epam.repository.UserRepository;
import com.epam.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
 class LoginServiceTest {

	@Mock
	UserRepository adminAndUserRepo;

	@InjectMocks
	UserServiceImpl loginService;

	@Mock
	ModelMapper mapper;

	User user;
	UserDto userDto;

	@BeforeEach
	public void setData() {
		user = new User("abc", "e2edq", "qfqf");
		userDto = new UserDto(1, "abc", "e2edq", "qfqf");

	}

	@Test
	void validateAdminTest() {

		Mockito.when(adminAndUserRepo.findByuserNameAndPassword(userDto.getUserName(), userDto.getPassword()))
				.thenReturn(Optional.of(user));
		Mockito.when(mapper.map(user, UserDto.class)).thenReturn(userDto);
		UserDto validatedUserDto = loginService.signIn(userDto);
		assertNotNull(validatedUserDto);
	}

	@Test
	void validateAdminWithInvalidCredentials() throws UserException {
		Mockito.when(adminAndUserRepo.findByuserNameAndPassword(userDto.getUserName(), userDto.getPassword()))
				.thenReturn(Optional.empty());
		assertThrows(UserException.class, () -> loginService.signIn(userDto));

	}

	@Test
	void userSignUpTest() {
		Mockito.when(mapper.map(userDto, User.class)).thenReturn(user);
		Mockito.when(adminAndUserRepo.existsByuserName(user.getUsername())).thenReturn(false);
		Mockito.when(adminAndUserRepo.save(user)).thenReturn(user);
		Mockito.when(mapper.map(user, UserDto.class)).thenReturn(userDto);
		UserDto savedUserDto = loginService.signUp(userDto);
		assertNotNull(savedUserDto);
	}

	@Test
	void userSignUpWithNotExistingUser() {
		Mockito.when(mapper.map(userDto, User.class)).thenReturn(user);
		Mockito.when(adminAndUserRepo.existsByuserName(user.getUsername())).thenReturn(true);
		assertThrows(UserException.class, () -> loginService.signUp(userDto));

	}
}
