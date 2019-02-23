package com.solutis.desafio.service.commons;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.solutis.desafio.service.commons.interfaces.GenericService;

@Component
public abstract class GenericServiceAb<Entity extends Object, ID extends Serializable>
		implements GenericService<Entity, ID> {

	private Class<?> classeDefault;

	public void setClasseDefault(Class<?> classeDefault) {
		this.classeDefault = classeDefault;
	}

	@Override
	@Transactional
	public Object save(Entity e) {
		boolean modoInserir = false;
		e = getRepository().save(e);
		getRepository().flush();
		return e;
	}

	@Override
	@Transactional
	public List<Entity> saveInBatch(List<Entity> e) {
		List<Entity> lista = getRepository().saveAll(e);
		getRepository().flush();
		return lista;
	}

	@Override
	@Transactional
	public void delete(Entity e) {
		getRepository().delete(e);
		getRepository().flush();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Object> findByPrimaryKey(ID id) {
		return Optional.ofNullable(getRepository().findById(id));
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

}
