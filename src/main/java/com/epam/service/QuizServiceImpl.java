package com.epam.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.QuizDto;
import com.epam.entity.Question;
import com.epam.entity.Quiz;
import com.epam.exceptions.QuizException;
import com.epam.repository.QuestionRepository;
import com.epam.repository.QuizRepository;

@Service
public class QuizServiceImpl implements QuizService {
	@Autowired
	QuizRepository quizRepo;
	@Autowired
	QuestionRepository questionRepo;
	@Autowired
	ModelMapper mapper;

	Logger logger = LogManager.getLogger(QuizServiceImpl.class);

	public QuizDto createQuiz(QuizDto quizDto, List<Integer> questionIds) {
		logger.info("inside the createQuiz method !!");
		Quiz quiz = mapper.map(quizDto, Quiz.class);
		if (!quizRepo.existsBytitle(quiz.getTitle())) {
			quiz.setQuestionLibrary(questionRepo.findAllById(questionIds));
			Quiz savedQuiz = quizRepo.save(quiz);
			logger.info("created Quiz Successfully with id : {} ", savedQuiz.getId());
			return mapper.map(savedQuiz, QuizDto.class);
		} else {
			logger.warn("Question with given title already exist:{} ", quizDto.getTitle());
			throw new QuizException("Quiz title already exits. Please try with another title");
		}
	}

	public void deleteQuiz(int id) {
		logger.info("inside the deleteQuiz method !!");
		quizRepo.deleteById(id);
	}

	public List<Quiz> getAllQuizzes() {
		logger.info("inside the getAllQuizzes method !!");
		return quizRepo.findAll();
	}

	public QuizDto getQuizByid(int id) throws QuizException {
		logger.info("inside the getQuizByid method !!");
		return quizRepo.findById(id).map(quiz -> {
			logger.info("Quiz with given id retrived successfully:{}", quiz);
			return mapper.map(quiz, QuizDto.class);
		}).orElseThrow(() -> {
			logger.warn("Quiz with given id is not exist : {}", id);
			return new QuizException("Quiz with id doesnt exist. Please try with another id");

		});
	}

	public QuizDto updateQuiz(QuizDto quizDto, List<Integer> questionIds) throws QuizException {
		logger.info("inside the updateQuiz method !!");
		Quiz quiz = mapper.map(quizDto, Quiz.class);
		return quizRepo.findById(quiz.getId()).map(q -> {
			List<Question> questionsList = questionRepo.findAllById(questionIds);
			quiz.setQuestionLibrary(questionsList);
			Quiz updatedQuiz = quizRepo.save(quiz);
			logger.info("quiz with given id updated Successfully : {}", updatedQuiz);
			return mapper.map(updatedQuiz, QuizDto.class);
		}).orElseThrow(() -> {
			logger.warn("Question with given id is not exist : {}", quizDto.getId());
			return new QuizException("Quiz with id doesnt exist. Please try with another id");
		});
	}
}
