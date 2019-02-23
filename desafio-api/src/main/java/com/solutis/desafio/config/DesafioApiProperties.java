package com.solutis.desafio.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("desafio-solutis")
public class DesafioApiProperties {

	private List<String> originsPermitidas = Arrays.asList("http://localhost:4200");

	private final Seguranca seguranca = new Seguranca();

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public List<String> getOriginsPermitidas() {
		return originsPermitidas;
	}

	public void setOriginsPermitidas(List<String> originsPermitidas) {
		this.originsPermitidas = originsPermitidas;
	}

	public static class Seguranca {

		private boolean enableHttps;

		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}

	}

}
