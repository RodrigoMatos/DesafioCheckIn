package br.com.desafio.service.commons.util;

import java.util.Locale;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class BusinessMessagesResolver {

	@Autowired
	@Qualifier("BusinessMessageSource")
	protected MessageSource businessMessageSource;

	@Autowired
	@Qualifier("ValidationMessageSource")
	protected MessageSource validationMessageSource;

	public String getMessageContent(String keyMessage) {
		return this.getMessageContent(keyMessage, null);
	}

	public String getMessageContent(String keyMessage, Object... messageParameters) {
		Locale locale;
		if (RequestContextHolder.getRequestAttributes() != null) {
			locale = this.getRequest().getLocale();
		} else {
			locale = LocaleContextHolder.getLocale();
		}
		return this.businessMessageSource.getMessage(keyMessage, messageParameters, locale);
	}

	private ServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public String getValidationMessageContent(String keyMessage) {
		return this.getValidationMessageContent(keyMessage, null);
	}

	public String getValidationMessageContent(String keyMessage, Object... messageParameters) {
		return this.validationMessageSource.getMessage(keyMessage, messageParameters, this.getRequest().getLocale());
	}

}
