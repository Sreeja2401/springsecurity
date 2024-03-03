package com.epam.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.epam.dto.QuestionDto;
import com.epam.entity.Question;
import com.epam.entity.Quiz;
import com.epam.exceptions.QuestionException;
import com.epam.repository.QuestionRepository;
import com.epam.service.QuestionServiceImpl;

@ExtendWith(MockitoExtension.class)
class QuestionLibraryServiceTest {

	@Mock
	private QuestionRepository questionsRepo;
	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private QuestionServiceImpl questionsLibraryService;

	Question question;
	QuestionDto questionDto;
	List<Question> questionList;
	List<QuestionDto> questionDtoList;

	@BeforeEach
	public void setUp() {

		questionDto = new QuestionDto(1, "extension for java", Arrays.asList(".c", ".java", ".py"), "Medium", "java",
				"8");

		question = new Question("extension for java", Arrays.asList(".c", ".java", ".py"), "Medium", "java", "8");
		questionList = new ArrayList<>(Arrays.asList(question));
		questionDtoList = new ArrayList<>(Arrays.asList(questionDto));
	}

	@Test
	void createQuestionTest() throws QuestionException {
		Mockito.when(questionsRepo.existsByquestionTitle(question.getQuestionTitle())).thenReturn(false);
		Mockito.when(modelMapper.map(questionDto, Question.class)).thenReturn(question);
		Mockito.when(questionsRepo.save(question)).thenReturn(question);
		QuestionDto savedQuestionDto = questionsLibraryService.createQuestion(questionDto);

	}

	@Test
	void createQuestionWithExistingTitle() throws QuestionException {
		Mockito.when(modelMapper.map(questionDto, Question.class)).thenReturn(question);
		Mockito.when(questionsRepo.existsByquestionTitle(question.getQuestionTitle())).thenReturn(true);
		assertThrows(QuestionException.class, () -> questionsLibraryService.createQuestion(questionDto));
		Mockito.verify(questionsRepo).existsByquestionTitle(question.getQuestionTitle());
	}

	@Test
	void deleteQuestionTest() throws QuestionException {
		question.setQuestionNumber(1);
		doNothing().when(questionsRepo).delete(question);
		questionsRepo.delete(question);
		verify(questionsRepo).delete(question);
		questionsLibraryService.deleteQuestion(1);

	}

	@Test
	void viewQuestionByIdTest() throws QuestionException {
		question.setQuestionNumber(1);
		Mockito.when(questionsRepo.findById(1)).thenReturn(Optional.of(question));
		Mockito.when(modelMapper.map(question, QuestionDto.class)).thenReturn(questionDto);
		QuestionDto retrievedQuestionDto = questionsLibraryService.getQuestionById(question.getQuestionNumber());
		assertNotNull(retrievedQuestionDto);
	}

	@Test
	void viewQuestionWithoutIdPresentTest() {
		Mockito.when(questionsRepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(QuestionException.class, () -> questionsLibraryService.getQuestionById(1));
		Mockito.verify(questionsRepo).findById(1);
	}

	@Test
	void viewAllQuestions() {
		Mockito.when(questionsRepo.findAll()).thenReturn(questionList);
		List<Question> retrievedQuestionsList = questionsLibraryService.getAllQuestions();
		Mockito.verify(questionsRepo).findAll();
		assertEquals(questionList, retrievedQuestionsList);

	}

	@Test
	void updateQuestion() throws QuestionException {
		question.setQuestionNumber(1);
		questionDto.setQuestionNumber(1);
		Mockito.when(questionsRepo.findById(question.getQuestionNumber())).thenReturn(Optional.of(question));
		Mockito.when(modelMapper.map(questionDto, Question.class)).thenReturn(question);
		Mockito.when(questionsRepo.save(question)).thenReturn(question);
		Mockito.when(modelMapper.map(question, QuestionDto.class)).thenReturn(questionDto);
		QuestionDto updatedQuestionDto = questionsLibraryService.updateQuestion(questionDto);
		assertEquals(updatedQuestionDto, questionDto);
	}

	@Test
	void updateQuestionWithNonExistingIdTest() throws QuestionException {
		Mockito.when(questionsRepo.findById(1)).thenReturn(Optional.empty());
		assertThrows(QuestionException.class, () -> questionsLibraryService.updateQuestion(questionDto));
	}

}
