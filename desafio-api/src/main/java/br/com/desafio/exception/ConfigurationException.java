package br.com.desafio.exception;

public class ConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 356008322724735817L;

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(Throwable rootCause) {
		super(rootCause);
	}

}
