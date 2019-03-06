package br.com.desafio.service.commons.util;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import br.com.desafio.exception.EnvironmentException;
import br.com.desafio.exception.http.BadRequestException;

@Component
public class BusinessExceptionHandler {

	@Autowired
	protected BusinessMessagesResolver businessMessagesResolver;

	public void treatException(Exception ex, String messageKey) {
		if (ex instanceof BadRequestException) {
			if (StringUtils.isBlank(messageKey)) {
				throw (BadRequestException) ex;
			} else {
				String customMessage = businessMessagesResolver.getMessageContent(messageKey);
				throw new BadRequestException(customMessage);
			}
		} else if (ex instanceof PersistenceException) {
			if (ex.getCause() instanceof ConstraintViolationException) {
				treatConstraintViolationException(((ConstraintViolationException) ex.getCause()).getErrorCode());
			} else {
				throw new EnvironmentException(ex);
			}
		} else if (ex instanceof DataIntegrityViolationException) {
			if (((DataIntegrityViolationException) ex).getRootCause() instanceof ConstraintViolationException) {
				treatConstraintViolationException(((ConstraintViolationException) ex.getCause()).getErrorCode());
			} else {
				throw new EnvironmentException(ex);
			}
		} else if (ex instanceof SQLIntegrityConstraintViolationException) {
			treatConstraintViolationException(((SQLIntegrityConstraintViolationException) ex).getErrorCode());
		} else {
			throw new EnvironmentException(ex);
		}
	}

	public void treatConstraintViolationException(int errorCode) {
		if (errorCode == 1) {// Unique constraint violation code at Oracle Database
			throw new BadRequestException(
					businessMessagesResolver.getMessageContent("error.uniqueConstraintViolation"));
		} else if (errorCode == 2292) {// Unique constraint violation code at Oracle Database
			throw new BadRequestException(businessMessagesResolver.getMessageContent("error.children.key.found"));
		} else if (errorCode == 2291) {// Unique constraint violation code at Oracle Database
			throw new BadRequestException(businessMessagesResolver.getMessageContent("error.parent.key.not.found"));
		} else if (errorCode == 1400) {// null at not null field at Oracle Database
			throw new BadRequestException(businessMessagesResolver.getMessageContent("error.null"));
		} else {
			throw new BadRequestException(businessMessagesResolver.getMessageContent("error.constraintViolation"));
		}
	}

}
