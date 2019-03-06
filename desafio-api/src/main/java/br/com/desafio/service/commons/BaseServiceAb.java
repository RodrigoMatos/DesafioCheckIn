package br.com.desafio.service.commons;

import java.util.Date;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import br.com.desafio.commons.MessagesI18n;
import br.com.desafio.exception.http.BadRequestException;
import br.com.desafio.service.commons.util.BusinessExceptionHandler;
import br.com.desafio.utils.DateUtil;

@Component
public abstract class BaseServiceAb extends MessagesI18n {

	/**
	 * LocaleResolver para definição de locale no contexto do spring
	 */
	@Autowired
	protected LocaleResolver localeResolver;

	@Autowired
	protected Validator validator;

	@Autowired
	protected BusinessExceptionHandler businessExceptionHandler;

	/**
	 * Método genérico de tratamento de exceção que exibe a mensagem da exception
	 *
	 * @param ex
	 *            Exception
	 */
	protected void treatException(Exception ex) {
		treatException(ex, null);
	}

	/**
	 * Método genérico de tratamento de exceção que exibe a mensagem da exception
	 *
	 * @param ex
	 * @param messageKey
	 *            Nome da mensagem a ser buscada
	 */
	protected void treatException(Exception ex, String messageKey) {
		this.businessExceptionHandler.treatException(ex, messageKey);
	}

	protected void validarDataInicialMenorQueFinal(Date dataInicial, Date dataFim) {
		try {
			DateUtil.validarDataInicialMenorQueFinal(dataInicial, dataFim);
		} catch (BadRequestException ex) {
			if (ex.getMessage().equals(DateUtil.MENSAGEM_ERRO_VALIDACAO_PERIODO)) {
				throw new BadRequestException(super.getMessageContent("error.periodo.invalido"));
			}
		}
	}

	protected void validateInterval(Date dataInicial, Date dataFim) {
		try {
			DateUtil.validateInterval(dataInicial, dataFim);
		} catch (BadRequestException ex) {
			if (ex.getMessage().equals(DateUtil.MENSAGEM_ERRO_VALIDACAO_PERIODO)) {
				throw new BadRequestException(super.getMessageContent("error.periodo.invalido"));
			}
		}
	}

}
