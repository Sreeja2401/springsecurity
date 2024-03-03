package com.epam.service;

import java.util.List;

import com.epam.dto.QuestionDto;
import com.epam.entity.Question;


interface QuestionService {
	public QuestionDto createQuestion(QuestionDto questionDto);
	public QuestionDto deleteQuestion(int questionNumber);
	public List<Question> getAllQuestions();
	public Question getQuestionById(int id);
	public QuestionDto updateQuestion(QuestionDto questionDto);
}
