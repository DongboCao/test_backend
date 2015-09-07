package com.test.candidate.service.exception;

import java.util.List;

import org.springframework.validation.ObjectError;

public class ErrorDTO {
	String type;
	String message;
	List<ObjectError> fieldErrors;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ObjectError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<ObjectError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
