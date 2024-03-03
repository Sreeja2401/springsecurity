package com.epam.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int questionNumber;
	@NonNull
	String questionTitle;
	@NonNull
	@ElementCollection(targetClass = String.class)
	public List<String> options;
	@NonNull
	String difficultyLevel;
	@NonNull
	String taggingTopics;
	@NonNull
	String answers;
}