package br.com.desafio.service.commons.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericService<Entity extends Object, ID extends Serializable> extends BusinessRules<Entity> {

	/**
	 * Método que retorna acesso ao Repository usado no Spring Data
	 * 
	 * @return TemplateGenericRepository {@linkplain TemplateGenericRepository}
	 */
	JpaRepository<Entity, ID> getRepository();

	/**
	 * Método que faz persistência (save or update behavior)
	 * 
	 * @param e
	 *            Entity
	 * @return Object Object
	 */
	Object save(Entity e);

	List<Entity> saveInBatch(List<Entity> e);

	/**
	 * Método que deleta uma entidade JPA
	 * 
	 * @param e
	 *            Entity
	 *
	 */
	void delete(Entity e);

	/**
	 * Método que faz busca por chave primária usando a estrutura do Spring data
	 * 
	 * @param id
	 *            ID
	 * @return Optional<Object> Optional<Object>
	 */
	Optional<Object> findByPrimaryKey(ID id);

	/**
	 * Método que faz busca de todas entidades no banco usando a estrutura do Spring
	 * data
	 * 
	 * @return List<Entity> List<Entity>
	 */
	List<Entity> findAll();

	/**
	 * Método que faz busca por filtro no banco usando a estrutura do Spring data
	 * 
	 * @return List<Entity> List<Entity>
	 */
	List<Entity> findByFilter(Entity e);

	/**
	 * Método que faz retorno total de registros daquela entidade
	 *
	 * @return Long Long
	 */
	Long count();

}
