package br.com.desafio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.desafio.entity.CheckInEntity;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInEntity, Long> {

	@Query("select entity from CheckInEntity entity inner join fetch entity.hospede hospede where entity.id = :codigo ")
	CheckInEntity findByPrimaryKey(@Param("codigo") Long codigo);

	@Query("select entity from CheckInEntity entity inner join fetch entity.hospede hospede order by entity.dataInicio ")
	List<CheckInEntity> findAllWithHospede();

}
