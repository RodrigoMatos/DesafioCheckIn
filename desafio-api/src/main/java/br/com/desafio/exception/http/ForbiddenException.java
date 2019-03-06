package br.com.desafio.exception.http;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

	private static final long serialVersionUID = -8296171132895045641L;

	public ForbiddenException(String msg) {
		super(msg);
	}

}
