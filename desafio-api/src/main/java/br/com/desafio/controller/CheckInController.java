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
import br.com.desafio.entity.CheckInEntity;
import br.com.desafio.entity.filter.CheckInFilter;
import br.com.desafio.exception.http.BadRequestException;
import br.com.desafio.service.CheckInService;

@RestController
@RequestMapping(value = ApiMapping.API_MAPPING)
public class CheckInController extends GenericControllerAb {

	private static final long serialVersionUID = -44641418227955523L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInController.class);

	@Autowired
	private CheckInService checkInService;

	@GetMapping(value = "/checkin")
	public List<CheckInEntity> findAll() {
		return this.checkInService.findAll();
	}

	@GetMapping(value = "/checkin/findById/{id}")
	public CheckInEntity findById(@PathVariable("id") Long id) {
		Optional<Object> op = this.checkInService.findByPrimaryKey(id);
		if (op.isPresent()) {
			return (CheckInEntity) op.get();
		}
		throw new BadRequestException(this.getMessageContent("erro.registro.naoEncontrado"));
	}

	@GetMapping("/checkin/findByFilterLazy")
	public PageDataTableCustom findByFilterLazy(CheckInFilter filter) {
		return this.checkInService.findByFilterLazy(filter);
	}

	@PostMapping("/checkin/save")
	public CheckInEntity save(@RequestBody CheckInEntity entity) {
		return this.checkInService.save(entity);
	}

}
