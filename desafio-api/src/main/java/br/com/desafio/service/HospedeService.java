package br.com.desafio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.commons.model.PageDataTableCustom;
import br.com.desafio.entity.HospedeEntity;
import br.com.desafio.entity.filter.HospedeFilter;
import br.com.desafio.repository.HospedeRepository;
import br.com.desafio.repository.custom.HospedeRepositoryCustom;
import br.com.desafio.service.commons.GenericServiceAb;

@Service
public class HospedeService extends GenericServiceAb<HospedeEntity, Long> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HospedeService.class);

	@Autowired
	private HospedeRepository repository;
	
	@Autowired
	private HospedeRepositoryCustom repositoryCustom;

	@Override
	public JpaRepository<HospedeEntity, Long> getRepository() {
		return this.repository;
	}
	
	@Transactional(readOnly = true)
	public PageDataTableCustom findByFilterLazy(HospedeFilter filter) {
		return this.repositoryCustom.findByFilterLazy(filter);
	}

}
