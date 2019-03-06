package br.com.desafio.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio.commons.model.PageDataTableCustom;
import br.com.desafio.entity.CheckInEntity;
import br.com.desafio.entity.filter.CheckInFilter;
import br.com.desafio.exception.http.BadRequestException;
import br.com.desafio.repository.CheckInRepository;
import br.com.desafio.repository.custom.CheckInRepositoryCustom;
import br.com.desafio.service.commons.GenericServiceAb;
import br.com.desafio.utils.DateUtil;

@Service
public class CheckInService extends GenericServiceAb<CheckInEntity, Long> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckInService.class);

	@Autowired
	private CheckInRepository repository;

	@Autowired
	private CheckInRepositoryCustom repositoryCustom;

	@Autowired
	private HospedeService hospedeService;

	@Override
	public JpaRepository<CheckInEntity, Long> getRepository() {
		return this.repository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CheckInEntity> findAll() {
		return this.repository.findAllWithHospede();
	}

	@Transactional(readOnly = true)
	public PageDataTableCustom findByFilterLazy(CheckInFilter filter) {
		return this.repositoryCustom.findByFilterLazy(filter);
	}

	@Override
	@Transactional
	public CheckInEntity save(CheckInEntity e) {
		if (e.getHospede() != null && e.getHospede().getCodigo() == null) {
			e.setHospede(this.hospedeService.save(e.getHospede()));
		}
		return super.save(e);
	}

	@Override
	protected void validate(CheckInEntity e) {
		if (e.getDataInicio().after(e.getDataFim())) {
			throw new BadRequestException(super.getMessageContent("error.periodo.invalido"));
		}
		if (e.getDataInicio().before(new Date())) {
			throw new BadRequestException(super.getMessageContent("error.data.inicio.menor.que.hoje"));
		}
		this.calcularValorTotal(e);
		super.validate(e);
	}

	private void calcularValorTotal(CheckInEntity e) {
		Calendar calIni = Calendar.getInstance();
		calIni.setTime(e.getDataInicio());

		BigDecimal total = BigDecimal.ZERO;
		Integer qtdDiarias = DateUtil.calcularDiferencaDatasEmDias(e.getDataInicio(), e.getDataFim()) + 1;
		Calendar calFim = Calendar.getInstance();
		calFim.setTime(e.getDataFim());

		if (calFim.get(Calendar.HOUR_OF_DAY) > 16
				|| (calFim.get(Calendar.HOUR_OF_DAY) == 16 && calFim.get(Calendar.MINUTE) >= 30)) {
			qtdDiarias++;
		}

		for (int i = 0; i < qtdDiarias; i++) {
			if (calIni.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
					|| calIni.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				total = total.add(new BigDecimal("150"));
				if (e.getAdicinalVeiculo()) {
					total = total.add(new BigDecimal("20"));
				}
			} else {
				total = total.add(new BigDecimal("120"));
				if (e.getAdicinalVeiculo()) {
					total = total.add(new BigDecimal("15"));
				}
			}
			calIni.add(Calendar.DAY_OF_MONTH, 1);
		}
		e.setValor(total);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Object> findByPrimaryKey(Long id) {
		return Optional.ofNullable(this.repository.findByPrimaryKey(id));
	}

}
