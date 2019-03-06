package br.com.desafio.repository.custom;

import java.util.ArrayList;

import javax.persistence.NoResultException;

import org.hibernate.query.NativeQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Component;

import br.com.desafio.commons.model.PageDataTableCustom;
import br.com.desafio.entity.HospedeEntity;
import br.com.desafio.entity.filter.HospedeFilter;
import br.com.desafio.repository.custom.commons.BaseComponentAb;

@Component
public class HospedeRepositoryCustom extends BaseComponentAb {

	public PageDataTableCustom findByFilterLazy(HospedeFilter filter) {

		StringBuilder jpqlCount = new StringBuilder();
		StringBuilder sqlFrom = new StringBuilder();
		PageDataTableCustom retorno = new PageDataTableCustom();

		sqlFrom.append(" FROM HOSPEDES ");
		sqlFrom.append("  	LEFT JOIN CHECKIN ON HOSPEDES.ID_HOSPEDE = CHECKIN.ID_HOSPEDE_FK ");
		sqlFrom.append(" WHERE (:nome is null OR upper(HOSPEDES.DS_NOME) LIKE upper(:nome)||'%') ");
		sqlFrom.append(" 	AND (:documento IS NULL OR HOSPEDES.DS_DOCUMENTO LIKE :documento) ");
		sqlFrom.append(" 	AND (:telefone IS NULL OR HOSPEDES.DS_TELEFONE LIKE :telefone) ");
		if (filter.getSomenteCheckIn()) {
			sqlFrom.append(
					" 	AND (EXISTS(SELECT 1 from CHECKIN CI WHERE CI.ID_HOSPEDE_FK = HOSPEDES.ID_HOSPEDE AND SYSDATE BETWEEN CI.DT_INICIO AND CI.DT_FIM )) ");
		}
		if (filter.getSomenteCheckOut()) {
			sqlFrom.append(
					" 	AND (NOT EXISTS(SELECT 1 from CHECKIN CI WHERE CI.ID_HOSPEDE_FK = HOSPEDES.ID_HOSPEDE AND SYSDATE BETWEEN CI.DT_INICIO AND CI.DT_FIM )) ");
		}

		jpqlCount.append(" SELECT COUNT(DISTINCT HOSPEDES.ID_HOSPEDE) ").append(sqlFrom);
		NativeQuery queryCount = this.getHibernateSession().createNativeQuery(jpqlCount.toString());
		queryCount.setParameter("nome", filter.getNome(), StringType.INSTANCE);
		queryCount.setParameter("documento", filter.getDocumento(), StringType.INSTANCE);
		queryCount.setParameter("telefone", filter.getTelefone(), StringType.INSTANCE);

		Long count;
		try {
			count = ((Number) queryCount.getSingleResult()).longValue();
		} catch (NoResultException e) {
			count = 0L;
		}
		if (count > 0) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("	HOSPEDES.ID_HOSPEDE, ");
			sql.append("	HOSPEDES.DS_NOME, ");
			sql.append("	HOSPEDES.DS_DOCUMENTO, ");
			sql.append("	HOSPEDES.DS_TELEFONE, ");
			sql.append("	SUM(CHECKIN.VL_VALOR) AS TOTAL_GASTO, ");
			sql.append(
					"	(SELECT MAX(LASTCHEKIN.VL_VALOR) FROM CHECKIN LASTCHEKIN WHERE LASTCHEKIN.ID_HOSPEDE_FK = HOSPEDES.ID_HOSPEDE) AS LAST_CHECKIN ");
			sql.append(sqlFrom).append(" GROUP BY HOSPEDES.ID_HOSPEDE  ").append(" ORDER BY HOSPEDES.ID_HOSPEDE ");
			NativeQuery query = this.getHibernateSession().createNativeQuery(sql.toString());
			query.setParameter("nome", filter.getNome(), StringType.INSTANCE);
			query.setParameter("documento", filter.getDocumento(), StringType.INSTANCE);
			query.setParameter("telefone", filter.getTelefone(), StringType.INSTANCE);
			this.setPaginator(query, filter);
			retorno.setListObject(
					super.resultSetToList(HospedeEntity.class, query.getResultList(), super.camposPreenchimento(
							"codigo", "nome", "documento", "telefone", "totalGasto", "totalUltimoCheckIn")));
		} else {
			retorno.setListObject(new ArrayList<>());
		}
		retorno.setLength(count.intValue());
		retorno.setPageSize(filter.getPageSize());
		retorno.setPageIndex(filter.getPageIndex());
		return retorno;
	}

}
