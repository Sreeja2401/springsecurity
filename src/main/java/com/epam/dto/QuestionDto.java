package com.epam.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
	int questionNumber;
	@NotBlank
	String questionTitle;
	@Size(min=2,max=10,message="There should be atleast two options")
    List<String> options;
	@Pattern(regexp = "^(?i)(easy|medium|hard)$",message="Difficulty level should be one among easy,medium and hard")
	String difficultyLevel;
	@NotBlank
	String taggingTopics;
	@NotBlank
	String answers;

}
