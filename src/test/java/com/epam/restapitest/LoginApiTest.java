package com.epam.restapitest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.UserDto;
import com.epam.restapi.LoginApi;
import com.epam.service.UserServiceImpl;
import com.epam.service.QuestionServiceImpl;
import com.epam.service.QuizServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LoginApi.class)
 class LoginApiTest {
	@MockBean
	UserServiceImpl loginService;

	@MockBean
	QuizServiceImpl quizService;

	@MockBean
	QuestionServiceImpl questionsService;

	@Autowired
	private MockMvc mockMvc;
	UserDto userDto;

	@BeforeEach
	public void setUp() {

		userDto = new UserDto(1, "user", "sreeja", "Sreeja@2401");
	}

	@Test
	void testSetUser() throws Exception {
		Mockito.when(loginService.signUp(userDto)).thenReturn(userDto);
		mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(userDto))).andExpect(status().isOk());

	}

}
