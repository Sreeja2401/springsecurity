package com.epam.dto;

import java.util.List;

import com.epam.entity.Question;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
	int id;
	String title;
	@Min(value = 1,message="question weightage should not be negative")
	int marks;
	List<Question> questionLibrary;
	
	
}
