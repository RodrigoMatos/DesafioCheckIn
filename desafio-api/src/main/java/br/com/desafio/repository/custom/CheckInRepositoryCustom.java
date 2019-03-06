package br.com.desafio.repository.custom;

import java.util.ArrayList;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import br.com.desafio.commons.model.PageDataTableCustom;
import br.com.desafio.entity.CheckInEntity;
import br.com.desafio.entity.filter.CheckInFilter;
import br.com.desafio.repository.custom.commons.BaseComponentAb;

@Component
public class CheckInRepositoryCustom extends BaseComponentAb {

	public PageDataTableCustom findByFilterLazy(CheckInFilter filter) {

		StringBuilder jpqlWhere = new StringBuilder();
		StringBuilder jpqlCount = new StringBuilder();
		PageDataTableCustom retorno = new PageDataTableCustom();

		jpqlCount.append("SELECT COUNT(1) FROM CheckInEntity entity ");
		jpqlCount.append(" INNER JOIN entity.hospede hospede ");

		jpqlWhere.append(" WHERE (:nome is null OR hospede.nome = :nome) ");
		jpqlWhere.append(" 	AND (:documento IS NULL OR hospede.documento = :documento) ");
		jpqlWhere.append(" 	AND (:telefone IS NULL OR hospede.telefone = :telefone) ");

		jpqlCount.append(jpqlWhere.toString());
		Query queryCount = this.getEntityManager().createQuery(jpqlCount.toString());
		queryCount.setParameter("nome", filter.getNome());
		queryCount.setParameter("documento", filter.getDocumento());
		queryCount.setParameter("telefone", filter.getTelefone());

		Long count = (Long) queryCount.getSingleResult();
		if (count > 0) {
			StringBuilder jpqlBuilder = new StringBuilder();
			jpqlBuilder.append(" SELECT entity FROM CheckInEntity entity INNER JOIN FETCH entity.hospede hospede ");
			jpqlBuilder.append(jpqlWhere.toString());
			jpqlBuilder.append(" ORDER BY entity.dataInicio ");
			TypedQuery<CheckInEntity> query = this.getEntityManager().createQuery(jpqlBuilder.toString(),
					CheckInEntity.class);
			query.setParameter("nome", filter.getNome());
			query.setParameter("documento", filter.getDocumento());
			query.setParameter("telefone", filter.getTelefone());
			this.setPaginator(query, filter);
			retorno.setListObject(query.getResultList());
		} else {
			retorno.setListObject(new ArrayList<>(0));
		}
		retorno.setLength(count.intValue());
		retorno.setPageSize(filter.getPageSize());
		retorno.setPageIndex(filter.getPageIndex());
		return retorno;
	}

}
