package com.epam.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.QuestionDto;
import com.epam.entity.Question;
import com.epam.service.QuestionServiceImpl;

import jakarta.validation.Valid;
@RequestMapping("questions")
@RestController
public class QuestionApi {

	@Autowired
	QuestionServiceImpl questionService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuestionDto> createQuestion(@Valid @RequestBody QuestionDto questionDto) {
		return new ResponseEntity<>(questionService.createQuestion(questionDto), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<QuestionDto> getQuestionById(@PathVariable int id) {
		return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<QuestionDto> deleteQuestion(@PathVariable int id) {
		questionService.deleteQuestion(id);
		return new ResponseEntity<>( HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<QuestionDto> updateQuestion(@Valid @RequestBody QuestionDto questionDto) {
		return new ResponseEntity<>(questionService.updateQuestion(questionDto), HttpStatus.CREATED);
	}

}
