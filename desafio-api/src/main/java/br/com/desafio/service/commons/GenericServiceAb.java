package br.com.desafio.service.commons;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.annotations.EntityConstraintsValid;
import br.com.desafio.annotations.FieldConstraintValid;
import br.com.desafio.exception.ConfigurationException;
import br.com.desafio.exception.http.BadRequestException;
import br.com.desafio.service.commons.interfaces.GenericService;
import br.com.desafio.utils.ReflexaoUtils;

@Component
public abstract class GenericServiceAb<Entity extends Object, ID extends Serializable> extends BaseServiceAb
		implements GenericService<Entity, ID> {

	private Class<?> classeDefault;

	public void setClasseDefault(Class<?> classeDefault) {
		this.classeDefault = classeDefault;
	}

	/**
	 * Método que realiza validação (Bean Validation
	 *
	 * @param Entity Entidade a ser validada
	 */
	protected void validate(Entity e) {
		Set<ConstraintViolation<Entity>> constraintViolations = validator.validate(e);
		if (!constraintViolations.isEmpty()) {
			if (constraintViolations.stream()
					.anyMatch(item -> item.getMessageTemplate().contains("javax.validation.constraints.NotBlank")
							|| item.getMessageTemplate().contains("javax.validation.constraints.NotNull"))) {
				throw new BadRequestException(getMessageContent("error.preencha.todos.os.campos.obrigatorios"));
			} else {
				throw new BadRequestException(getValidationMessage(constraintViolations));
			}
		}
	}

	public void validationSaveInBatch(List<Entity> e) {
		validate(e);
	}

	@Override
	public void validationSave(Entity e) {
		validate(e);
	}

	/**
	 * Método que converte as constraints em mensagem de validação (Bean Validation)
	 *
	 * @param Entity Entidade a ser validada
	 * @return String texto das mensagens de validaçao
	 */
	private String getValidationMessage(Set<ConstraintViolation<Entity>> constraintViolations) {
		StringBuilder buffer = new StringBuilder();
		for (ConstraintViolation<Entity> constraintViolation : constraintViolations) {
			if (constraintViolation.getMessageTemplate().contains("{javax.validation.constraints.")) {
				if (constraintViolation.getPropertyPath() != null) {
					buffer.append(super.getMessageContent(String.valueOf(constraintViolation.getPropertyPath())))
							.append(": ");
				}
				buffer.append(this.getMessageByAnnotationValidationFail(constraintViolation)).append(".<br>");
			} else {
				buffer.append(this.getMessageContent(constraintViolation.getMessage())).append(".<br>");
			}
		}
		return buffer.toString();
	}

	private String getMessageByAnnotationValidationFail(ConstraintViolation<Entity> constraintViolation) {

		String messagem = constraintViolation.getMessage();
		Map<String, Object> attributos = constraintViolation.getConstraintDescriptor().getAttributes();
		messagem = super.getValidationMessageContent(messagem);

		if (messagem.contains("{")) {
			Pattern pattern = Pattern.compile("\\{\\w+\\}");
			Matcher matcher = pattern.matcher(messagem);
			String chave = null;
			Object valor = null;
			while (matcher.find()) {
				chave = matcher.group();
				valor = attributos.get((chave.replace("{", "").replace("}", "")));
				messagem = messagem.replace(chave, valor != null ? valor.toString() : "0");
			}
		}
		return messagem;
	}

	/**
	 * Método que realiza validação (Bean Validation
	 *
	 * @param List<Entity> Lista de entidades a serem validada
	 */
	protected void validate(List<Entity> e) {

		StringBuilder buffer = new StringBuilder();
		for (Entity entity : e) {
			try {
				validate(entity);
			} catch (BadRequestException ex) {
				if (buffer.indexOf(ex.getMessage()) == -1) {
					buffer.append(ex.getMessage()).append("\n");
				}
			}
		}
		if (StringUtils.isNotBlank(buffer)) {
			throw new BadRequestException(buffer.toString());
		}
	}

	@Override
	public void validationDelete(Entity e) {
		try {
			if (ReflexaoUtils.getValorAtributoPk(e) == null) {
				throw new BadRequestException(getMessageContent("javax.validation.constraints.Past.message"));
			}
		} catch (Exception ex) {
			throw new ConfigurationException(ex);
		}
	}

	@Override
	@Transactional
	public Entity save(Entity e) {
		boolean modoInserir = false;
		try {
			if (ReflexaoUtils.getValorAtributoPk(e) == null) {
				modoInserir = true;
			}
		} catch (Exception ex) {
			// ignorar erro
		}
		try {
			validationSave(e);
			e = getRepository().save(e);
			getRepository().flush();
		} catch (Exception ex) {
			if (modoInserir) {
				try {
					ReflexaoUtils.executarMetodoSet(e, "codigo", null);
				} catch (Exception ex1) {
					// ignorar
				}
			}
			treatException(ex);
		}
		return e;
	}

	@Override
	@Transactional
	public List<Entity> saveInBatch(List<Entity> e) {
		List<Entity> indexNew = new ArrayList<>(0);
		try {
			validationSaveInBatch(e);
			e.forEach(temp -> {
				try {
					if (ReflexaoUtils.getValorAtributoPk(temp) == null) {
						indexNew.add(temp);
					}
				} catch (Exception ex) {
				}
			});
			List<Entity> lista = getRepository().saveAll(e);
			getRepository().flush();
			return lista;
		} catch (Exception ex) {
			if (!indexNew.isEmpty()) {
				indexNew.forEach(temp -> {
					try {
						ReflexaoUtils.setValuePkEntity(temp, null);
					} catch (Exception ex1) {
					}
				});
			}
			treatException(ex);
		}
		return new ArrayList<>(0);
	}

	@Override
	@Transactional
	public void delete(Entity e) {
		try {
			validationDelete(e);
			getRepository().delete(e);
			getRepository().flush();
		} catch (Exception ex) {
			treatException(ex);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Object> findByPrimaryKey(ID id) {
		return (Optional) getRepository().findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Entity> findAll() {
		return getRepository().findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Entity> findByFilter(Entity e) {
		Example<Entity> example = Example.of(e);
		return getRepository().findAll(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return getRepository().count();
	}

	protected Long obterCodigo(Object entity) {
		return (Long) obterValorProp(entity, "codigo");
	}

	protected Object obterValorProp(Object entity, String prop) {
		return ReflexaoUtils.executarMetodoGet(entity, prop);
	}

	@Override
	protected void treatException(Exception ex) {
		if ((ex instanceof PersistenceException || ex instanceof DataIntegrityViolationException) && ex.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException constraintException = (ConstraintViolationException) ex.getCause();
			if (testConstraintViolated(constraintException.getErrorCode())) {
				ex = treatExceptionConstraint(ex, constraintException);
			}
		}
		super.treatException(ex);
	}

	private Exception treatExceptionConstraint(Exception ex, ConstraintViolationException constraintException) {
		String constraintName = constraintException.getConstraintName();
		if (constraintName != null) {
			if (constraintName.contains(".")) {
				constraintName = constraintName.split("\\.")[1];
			}
			Class<?> theType = null;
			if (this.classeDefault != null) {
				theType = this.classeDefault;
			} else {
				theType = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass())
						.getActualTypeArguments()[0];
			}
			EntityConstraintsValid constraintsValid = theType.getAnnotation(EntityConstraintsValid.class);
			if (constraintsValid != null) {
				for (FieldConstraintValid valid : constraintsValid.value()) {
					if (valid.constraint().trim().equals(constraintName.trim())) {
						ex = new BadRequestException(this.getMessageContent(valid.chaveMsg()));
						break;
					}
				}
			}
		}
		return ex;
	}

	private boolean testConstraintViolated(int errorCode) {
		return errorCode == 1 || errorCode == 2292 || errorCode == 2291;
	}

	protected BigDecimal getBigDecimalNumber(BigDecimal number) {
		if (number == null) {
			return BigDecimal.ZERO;
		}
		return number;
	}

}
