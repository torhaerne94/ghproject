package com.ghproject.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatusCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFoundException extends RuntimeException {
	private HttpStatusCode responseCode;
	private String message;

	public ResourceNotFoundException(final HttpStatusCode responseCode, final String message) {
		this.responseCode = responseCode;
		this.message = message;
	}

}
