package com.ghproject.exception;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class SimpleExceptionDTO {
	private HttpStatusCode responseCode;
	private String message;

	public SimpleExceptionDTO(final HttpStatusCode responseCode, final String message) {
		this.responseCode = responseCode;
		this.message = message;
	}
}
