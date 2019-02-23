package com.solutis.desafio.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.solutis.desafio.entity.ProcessoEntity;
import com.solutis.desafio.exceptions.BadRequestException;
import com.solutis.desafio.repository.ProcessoRepository;
import com.solutis.desafio.service.commons.GenericServiceAb;

@Service
public class ProcessoService extends GenericServiceAb<ProcessoEntity, Long> {

	@Autowired
	private ProcessoRepository repository;

	@Override
	public JpaRepository<ProcessoEntity, Long> getRepository() {
		return this.repository;
	}

	@Transactional
	public ProcessoEntity processar(ProcessoEntity entity) {
		this.processarConteudo(entity);
		this.save(entity);
		return entity;
	}

	private void validarProcessamento(ProcessoEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getString())) {
			throw new BadRequestException("O campo String é de preenchimento obrigatório");
		}
		if (entity.getString().length() < 3) {
			throw new BadRequestException("A string deve conter ao menos 3 letras");
		}
	}

	private void processarConteudo(ProcessoEntity entity) {

		Long tempoInicial = new Date().getTime();
		this.validarProcessamento(entity);
		boolean encontrou = false;

		String conteudo = entity.getString();

		int length = conteudo.length();
		// INICIAR O FOR DA 3 POSIÇÃO PARA VERIFICAR AS DUAS LETRAS ANTERIORES
		for (int i = 2; i < length; i++) {
			if (this.obterQtdLetraRepetida(conteudo, conteudo.charAt(i)).equals(1) && this.isVogal(conteudo.charAt(i))
					&& !this.isVogal(conteudo.charAt(i - 1)) && this.isVogal(conteudo.charAt(i - 2))) {
				encontrou = true;
				entity.setVogal(conteudo.charAt(i));
				break;
			}
		}
		if (!encontrou) {
			throw new BadRequestException("Não foi encontrada nenhuma vogal valida");
		}
		Long tempoFinal = new Date().getTime() - tempoInicial;
		entity.setTempoTotal(tempoFinal);
	}

	private boolean isVogal(char e) {
		// Obter a letra em minúsculo
		e = Character.toLowerCase(e);
		// Verificar se é vogal
		return e == 'a' || e == 'e' || e == 'i' || e == 'o' || e == 'u';
	}

	private Integer obterQtdLetraRepetida(String conteudo, Character letra) {
		// Filtrar letras iguais a informada no parametro e contar as repetidas
		Long qtd = conteudo.chars().mapToObj(item -> (char) item)
				.filter(item -> Character.toLowerCase(item) == Character.toLowerCase(letra)).count();
		// Retornar a quantidade de letras repetidas
		return qtd.intValue();
	}

}
