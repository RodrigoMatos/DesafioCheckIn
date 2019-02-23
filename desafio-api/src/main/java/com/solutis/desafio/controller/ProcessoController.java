package com.solutis.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solutis.desafio.constraints.ApiMapping;
import com.solutis.desafio.controller.commons.GenericControllerAb;
import com.solutis.desafio.entity.ProcessoEntity;
import com.solutis.desafio.service.ProcessoService;

@RestController
@RequestMapping(value = ApiMapping.API_MAPPING)
public class ProcessoController extends GenericControllerAb {

	private static final long serialVersionUID = -3580071922201225749L;

	@Autowired
	private ProcessoService processoService;

	@PostMapping(value = "/processo/processar")
	public ResponseEntity<ProcessoEntity> processar(@RequestBody ProcessoEntity entity) {
		this.processoService.processar(entity);
		return ResponseEntity.ok().body(entity);
	}

	@GetMapping(value = "/processo/findAll")
	public List<ProcessoEntity> findAll() {
		return this.processoService.findAll();
	}

}
