package br.com.desafio.exception;

public class EnvironmentException extends RuntimeException {

	private static final long serialVersionUID = 854195039069288097L;

	public EnvironmentException(String message) {
		super(message);
	}

	public EnvironmentException(Throwable rootCause) {
		super(rootCause);
	}

}
