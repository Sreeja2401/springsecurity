package com.epam.service;

import java.util.List;

import com.epam.dto.QuizDto;
import com.epam.entity.Quiz;

public interface QuizService {
	public QuizDto createQuiz(QuizDto quizDto, List<Integer> questionIds);
	public void deleteQuiz(int id);
	public List<Quiz> getAllQuizzes();
	public QuizDto getQuizByid(int id);
	public QuizDto updateQuiz(QuizDto quizDto, List<Integer> ids);
}

	