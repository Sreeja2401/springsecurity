package com.epam.restapitest;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.dto.QuestionDto;
import com.epam.entity.Question;
import com.epam.restapi.QuestionApi;
import com.epam.service.QuestionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(QuestionApi.class)
 class QuestionsApiTest {

		@MockBean
		QuestionServiceImpl questionLibraryService;
		@Autowired
		private MockMvc mockMvc;
		Question question;
		QuestionDto questionDto;

		@BeforeEach
		public void setUp() {
			question=new Question("hello",List.of("1", "2"),"easy","numbers","1");
			questionDto=new QuestionDto(1, "hello",List.of("1", "2"),"easy","numbers","1");
		}

		@Test
		 void viewAllQuestions() throws Exception {
			Mockito.when(questionLibraryService.getAllQuestions()).thenReturn(List.of(question));
			 mockMvc.perform(get("/questions"))
					.andExpect(status().isOk())
		            .andExpect(jsonPath("$[0].questionTitle").value("hello"))
		            .andReturn();
	    	
		}
		@Test
		 void viewQuestionByID() throws Exception {
			Mockito.when(questionLibraryService.getQuestionById(1)).thenReturn(questionDto);
			 mockMvc.perform(get("/questions/{questionNumber}",1))
					.andExpect(status().isOk())
		            .andExpect(jsonPath("$.questionTitle").value("hello"))
		            .andReturn();
	   	
		}
		@Test
		void testAddQuestions() throws Exception {
			Mockito.when(questionLibraryService.createQuestion(questionDto)).thenReturn(questionDto);
			mockMvc.perform(post("/questions")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(questionDto)))
	                .andExpect(status().isCreated());
	              
	               
		}
		@Test
		void testDeleteQuestion() throws  Exception {
			doNothing().when(questionLibraryService).deleteQuestion(1);
			mockMvc.perform(delete("/questions/{questionNumber}",1))
			 .andExpect(status().isNoContent());         
			
		}
		@Test
		void testModifyQuestion() throws Exception {
			Mockito.when(questionLibraryService.updateQuestion(questionDto)).thenReturn(questionDto);
			mockMvc.perform(put("/questions")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(questionDto)))
	                .andExpect(status().isCreated());          
		}


	}


