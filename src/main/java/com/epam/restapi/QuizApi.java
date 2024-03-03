package com.epam.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.epam.dto.QuizDto;
import com.epam.entity.Quiz;
import com.epam.service.QuizService;

import jakarta.validation.Valid;
@RequestMapping("quizzes")
@RestController
public class QuizApi {

	@Autowired
	private QuizService quizService;

	@GetMapping
	public ResponseEntity<List<Quiz>> getAllQuizzes() {
		return new ResponseEntity<>(quizService.getAllQuizzes(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<QuizDto> saveQuiz(@Valid @RequestBody QuizDto quizDto,
			@RequestParam("questionIds") List<Integer> questionIds) {
		return new ResponseEntity<>(quizService.createQuiz(quizDto, questionIds), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<QuizDto> getQuizById(@PathVariable int id){
		return new ResponseEntity<>(quizService.getQuizByid(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteQuiz(@PathVariable int id){
		quizService.deleteQuiz(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<QuizDto> updateQuiz(@Valid @RequestBody QuizDto quizDto , @RequestParam("questionIds") List<Integer> questionIds) {
		return new ResponseEntity<>(quizService.updateQuiz(quizDto, questionIds), HttpStatus.CREATED);
		}
}
