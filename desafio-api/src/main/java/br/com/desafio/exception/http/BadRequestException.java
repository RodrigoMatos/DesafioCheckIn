package br.com.desafio.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -8296171132895045641L;

	public BadRequestException(String msg) {
		super(msg);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
