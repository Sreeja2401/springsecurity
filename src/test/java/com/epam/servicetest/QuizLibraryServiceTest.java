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
import com.epam.dto.QuizDto;
import com.epam.entity.Question;
import com.epam.entity.Quiz;
import com.epam.exceptions.QuizException;
import com.epam.repository.QuestionRepository;
import com.epam.repository.QuizRepository;
import com.epam.service.QuizServiceImpl;

@ExtendWith(MockitoExtension.class)
class QuizLibraryServiceTest {
	@Mock
	private QuizRepository quizRepository;
	@Mock
	private ModelMapper modelMapper;
	@Mock
	private QuestionRepository questionRepository;

	@InjectMocks
	private QuizServiceImpl quizLibraryService;
	List<Quiz> quizList;
	Quiz quiz1;
	QuizDto quiz1Dto;
	Question question;

	@BeforeEach
	public void setUp() {
		question = new Question("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a");
		question.setQuestionNumber(1);

		quiz1 = new Quiz("vjit", 65);

		quiz1.setQuestionLibrary(Arrays.asList(question));
		quiz1Dto = new QuizDto(1, "vjit", 65,
				Arrays.asList(new Question("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a")));

		quizList = new ArrayList<>();
		quizList.add(quiz1);

	}

	@Test
	void addQuiz() {

		Mockito.when(modelMapper.map(quiz1Dto, Quiz.class)).thenReturn(quiz1);
		Mockito.when(quizRepository.existsBytitle(quiz1.getTitle())).thenReturn(false);
		Mockito.when(questionRepository.findAllById(Arrays.asList(1))).thenReturn(Arrays.asList(question));
		Mockito.when(quizRepository.save(quiz1)).thenReturn(quiz1);
		Mockito.when(modelMapper.map(quiz1, QuizDto.class)).thenReturn(quiz1Dto);
		QuizDto retrievedQuiz = quizLibraryService.createQuiz(quiz1Dto, Arrays.asList(1));
		assertEquals(retrievedQuiz, quiz1Dto);
	}

	@Test
	void addQuizWithDuplicateTitle() {

		Mockito.when(modelMapper.map(quiz1Dto, Quiz.class)).thenReturn(quiz1);
		Mockito.when(quizRepository.existsBytitle(quiz1.getTitle())).thenReturn(true);
		assertThrows(QuizException.class, () -> quizLibraryService.createQuiz(quiz1Dto, Arrays.asList(1, 2)));
	}

	@Test
	void viewAllQuizTest() {
		Mockito.when(quizRepository.findAll()).thenReturn(quizList);
		List<Quiz> retrievedQuizList = quizLibraryService.getAllQuizzes();
		assertEquals(quizList, retrievedQuizList);
	}

	@Test
	void viewQuizByExistingIdTest() {
		quiz1.setId(1);
		Mockito.when(quizRepository.findById(quiz1.getId())).thenReturn(Optional.of(quiz1));
		Mockito.when(modelMapper.map(quiz1, QuizDto.class)).thenReturn(quiz1Dto);
		QuizDto retrievedQuizDto = quizLibraryService.getQuizByid(quiz1.getId());
		assertNotNull(retrievedQuizDto);
	}

	@Test
	void viewQuizByNotExistingIdTest() {
		int id = 1;
		Mockito.when(quizRepository.findById(id)).thenReturn(Optional.empty());
		assertThrows(QuizException.class, () -> quizLibraryService.getQuizByid(id));

	}

	@Test
	void deleteQuizWithExistingId() {
		quiz1.setId(1);

		doNothing().when(quizRepository).delete(quiz1);

		quizRepository.delete(quiz1);
		verify(quizRepository).delete(quiz1);
		quizLibraryService.deleteQuiz(1);

	}

}
