package br.com.desafio.configuration;

import java.util.Locale;

import javax.validation.Validator;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import br.com.desafio.constraints.GeneralConstants;

@Configuration
public class MessageLocaleConfig {

	/**
	 * Atributo que define Locale Padrão da aplicação
	 * 
	 */
	public static final Locale DEFAULT_LOCALE = new Locale(GeneralConstants.LOCALE_DEFAULT);

	/**
	 * Método que cria bean para manipulação de mensagens (I18N - Projeto deve ter
	 * suporte a internacionalização).
	 * 
	 * @return MessageSource MessageSource
	 */
	@Bean(name = "BusinessMessageSource")
	public MessageSource getBusinessMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:config/business_message");
		messageSource.setDefaultEncoding(GeneralConstants.CHARSET_DEFAULT);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean(name = "ValidationMessageSource")
	public MessageSource getValidationMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:ValidationMessages");
		messageSource.setDefaultEncoding(GeneralConstants.CHARSET_DEFAULT);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/**
	 * Método que cria bean para multiplos locale (I18N - Projeto deve ter suporte a
	 * internacionalização).
	 * 
	 * @return LocaleResolver LocaleResolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver resolver = new SessionLocaleResolver();
		resolver.setDefaultLocale(DEFAULT_LOCALE);
		return resolver;
	}

	/**
	 * Método que habilita o suporte do Spring a JSR 303 (Bean validation)
	 * 
	 * @return Validator
	 */
	@Bean
	public Validator validator() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setValidationMessageSource(getBusinessMessageSource());
		return localValidatorFactoryBean;
	}

}
