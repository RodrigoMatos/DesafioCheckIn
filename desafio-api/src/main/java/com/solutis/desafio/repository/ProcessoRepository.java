package com.solutis.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.solutis.desafio.entity.ProcessoEntity;

@Repository
public interface ProcessoRepository extends JpaRepository<ProcessoEntity, Long> {

}
