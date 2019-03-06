package br.com.desafio.service.commons.interfaces;

public interface BusinessRules<Entity extends Object> {

	/**
	 * Implementação padrão é não ter validação
	 * 
	 * @param Entity
	 *            - parametro a ser validado
	 */
	void validationSave(Entity e);

	/**
	 * Implementação padrão é não ter validação
	 * 
	 * @param Entity
	 *            - parametro a ser validado
	 */
	void validationDelete(Entity e);

}
