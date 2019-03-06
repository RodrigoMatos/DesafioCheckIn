package br.com.desafio.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.commons.model.PageDataTableCustom;
import br.com.desafio.constraints.ApiMapping;
import br.com.desafio.controller.commons.GenericControllerAb;
import br.com.desafio.entity.HospedeEntity;
import br.com.desafio.entity.filter.HospedeFilter;
import br.com.desafio.exception.http.BadRequestException;
import br.com.desafio.service.HospedeService;

@RestController
@RequestMapping(value = ApiMapping.API_MAPPING)
public class HospedeController extends GenericControllerAb {

	private static final long serialVersionUID = -44641418227955523L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HospedeController.class);

	@Autowired
	private HospedeService hospedeService;

	@GetMapping(value = "/hospede")
	public List<HospedeEntity> findAll() {
		return this.hospedeService.findAll();
	}

	@GetMapping(value = "/hospede/findById/{id}")
	public HospedeEntity findById(@PathVariable("id") Long id) {
		Optional<Object> op = this.hospedeService.findByPrimaryKey(id);
		if (op.isPresent()) {
			return (HospedeEntity) op.get();
		}
		throw new BadRequestException(this.getMessageContent("erro.registro.naoEncontrado"));
	}

	@GetMapping("/hospede/findByFilterLazy")
	public PageDataTableCustom findByFilterLazy(HospedeFilter filter) {
		return this.hospedeService.findByFilterLazy(filter);
	}

	@PostMapping("/hospede/save")
	public HospedeEntity save(@RequestBody HospedeEntity entity) {
		return this.hospedeService.save(entity);
	}

}
