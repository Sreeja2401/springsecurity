package com.epam.restapitest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.dto.QuizDto;
import com.epam.entity.Question;
import com.epam.entity.Quiz;
import com.epam.restapi.QuizApi;
import com.epam.service.UserService;
import com.epam.service.QuestionServiceImpl;
import com.epam.service.QuizService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(QuizApi.class)
 class QuizApiTest {

	@MockBean
	QuizService quizLibraryService;
	@MockBean
	QuestionServiceImpl questionsLibraryService;

	@MockBean
	UserService loginService;

	@Autowired
	private MockMvc mockMvc;

	List<Question> nationalsport;
	QuizDto quizDto;
	Quiz quiz;

	@BeforeEach
	public void setUp() {

		nationalsport = Arrays.asList(new Question("national sport of India",
				Arrays.asList("hockey", "cricket"), "sports", "easy", "hockey"));
		quiz = new Quiz("sportsQuiz", 2);
		quiz.setQuestionLibrary(nationalsport);
		quizDto = new QuizDto(2, "sportsQuiz", 2, nationalsport);

	}

	@Test
	void viewAllQuizes() throws Exception {
		Mockito.when(quizLibraryService.getAllQuizzes()).thenReturn(List.of(quiz));
		MvcResult mvcResult = mockMvc.perform(get("/quizzes")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title").value("sportsQuiz")).andReturn();
		assertNotNull(mvcResult);
	}

	@Test
	void viewQuizByID() throws Exception {
		Mockito.when(quizLibraryService.getQuizByid(1)).thenReturn(quizDto);
		MvcResult mvcResult = mockMvc.perform(get("/quizzes/{id}", 1)).andExpect(status().isOk())
				.andExpect(jsonPath("$.title").value("sportsQuiz")).andReturn();
		assertNotNull(mvcResult);
	}

	@Test
	void testAddQuiz() throws Exception {
		Mockito.when(quizLibraryService.createQuiz(any(), eq(List.of(1)))).thenReturn(quizDto);
		// Mockito.when(quizLibraryService.addQuiz(quizDto)).thenReturn(quizDto);
		mockMvc.perform(post("/quizzes").param("questionIds", "1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(quizDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("sportsQuiz"));

	}

	@Test
	void testDeleteQuiz() throws Exception {
		doNothing().when(quizLibraryService).deleteQuiz(1);
		mockMvc.perform(delete("/quizzes/{id}", 1));

	}

	@Test
	void testModifyQuestion() throws Exception {
		Mockito.when(quizLibraryService.updateQuiz(any(), eq(List.of(1)))).thenReturn(quizDto);
		// Mockito.when(quizLibraryService.modifyQuiz(quizDto)).thenReturn(quizDto);
		mockMvc.perform(put("/quizzes").param("questionIds", "1").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(quizDto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("sportsQuiz"));

	}

}
