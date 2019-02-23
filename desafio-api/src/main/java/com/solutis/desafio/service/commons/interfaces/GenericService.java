package com.solutis.desafio.service.commons.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GenericService<Entity extends Object, ID extends Serializable> {

	JpaRepository<Entity, ID> getRepository();

	Object save(Entity e);

	List<Entity> saveInBatch(List<Entity> e);

	void delete(Entity e);

	Optional<Object> findByPrimaryKey(ID id);

	List<Entity> findAll();

	List<Entity> findByFilter(Entity e);

	Long count();

}
