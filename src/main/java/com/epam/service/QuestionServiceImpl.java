package com.epam.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.QuestionDto;
import com.epam.entity.Question;
import com.epam.exceptions.QuestionException;
import com.epam.repository.QuestionRepository;

@Service
public class QuestionServiceImpl {

	@Autowired
	QuestionRepository questionsRepo;
	@Autowired
	ModelMapper mapper;
	
	Logger logger = LogManager.getLogger(QuestionServiceImpl.class);
    
	public QuestionDto createQuestion(QuestionDto questionDto) throws QuestionException {
		logger.info("inside the createQuestion method !!");
		Question question = mapper.map(questionDto, Question.class);
		if (!questionsRepo.existsByquestionTitle(questionDto.getQuestionTitle())) {
			Question savedQuestion = questionsRepo.save(question);
			logger.info("created Question Successfully with id : {} ", savedQuestion.getQuestionNumber());
			return mapper.map(savedQuestion, QuestionDto.class);
		} else {
			logger.warn("Question with given title already exist:{} ", questionDto.getQuestionTitle());
			throw new QuestionException("question with given title already exist");
		}
	}

	public void deleteQuestion(int id) {
		logger.info("inside the deleteQuestion method !!");
		questionsRepo.deleteById(id);

	}

	public List<Question> getAllQuestions() {
		logger.info("inside the getAllQuestion method !!");
		return questionsRepo.findAll();
	}

	public QuestionDto getQuestionById(int id) throws QuestionException {
		logger.info("inside the getQuestionById method !!");
		return questionsRepo.findById(id).map(question -> {
			logger.info("Question with given id retrived successfully:{}", question);
			return mapper.map(question, QuestionDto.class);
		}).orElseThrow(() -> {
			logger.warn("Question with given id is not exist : {}", id);
			return new QuestionException("Question with given id not found");
		});

	}

	public QuestionDto updateQuestion(QuestionDto questionDto) throws QuestionException {
		logger.info("inside the updateQuestion method !!");
		Question question = mapper.map(questionDto, Question.class);
		return questionsRepo.findById(question.getQuestionNumber()).map(q -> {
			Question updatedQuestion = questionsRepo.save(question);
			logger.info("Question with given id updated Successfully : {}", updatedQuestion);
			return mapper.map(updatedQuestion, QuestionDto.class);
		}).orElseThrow(() -> {
			logger.warn("Question with given id is not exist : {}", questionDto.getQuestionNumber());
			return new QuestionException("trying to update the Question which is not found");
		});

	}
}
