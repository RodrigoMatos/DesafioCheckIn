package br.com.desafio.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafio.service.commons.util.BusinessMessagesResolver;

@Component
public abstract class MessagesI18n {

	/**
	 * MessageSource para uso de messages pensando no projeto internacionalizado
	 */
	@Autowired
	protected BusinessMessagesResolver businessMessagesResolver;

	protected String getMessageContent(String keyMessage) {
		return this.businessMessagesResolver.getMessageContent(keyMessage);
	}

	protected String getMessageContent(String keyMessage, Object... messageParameters) {
		return this.businessMessagesResolver.getMessageContent(keyMessage, messageParameters);
	}

	protected String getValidationMessageContent(String keyMessage) {
		return this.businessMessagesResolver.getValidationMessageContent(keyMessage);
	}

	protected String getValidationMessageContent(String keyMessage, Object... messageParameters) {
		return this.businessMessagesResolver.getValidationMessageContent(keyMessage, messageParameters);
	}

}
