package com.epam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	private String timeStamp;
	private String status;
	private String error;
    private String path;
	
}