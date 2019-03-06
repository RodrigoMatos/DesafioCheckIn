package br.com.desafio.repository.custom.commons;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;

import br.com.desafio.commons.model.PaginatorCustom;
import br.com.desafio.exception.EnvironmentException;
import br.com.desafio.utils.ReflexaoUtils;
import br.com.desafio.utils.model.ConvertType;

public abstract class BaseComponentAb {

	@PersistenceContext
	protected EntityManager entityManager;

	protected Session getHibernateSession() {
		return this.entityManager.unwrap(Session.class);
	}

	protected String[] camposPreenchimento(String... campos) {
		return campos;
	}

	/**
	 * @param classe
	 *            - Tipo da classe que será retornada
	 * @param consulta
	 *            - ResultSet com o retorno da consulta
	 * @param campos
	 *            - Campos que serão setados para o objeto de retorno.
	 * @return Retorna os objetos instanciados para o resultSet
	 * @author Rodrigo.Matos
	 */
	protected Object resultSetToObject(Class<?> classe, List<Object[]> consulta, String[] campos) {
		return this.resultSetToObject(classe, consulta, campos, true, true);
	}

	/**
	 * @param classe
	 *            - Tipo da classe que será retornada
	 * @param consulta
	 *            - ResultSet com o retorno da consulta
	 * @param campos
	 *            - Campos que serão setados para o objeto de retorno.
	 * @return Retorna os objetos instanciados caso o valor do resultSet não seja
	 *         nulo
	 * @author Rodrigo.Matos
	 */
	protected Object resultSetToObjectWithoutInstance(Class<?> classe, List<Object[]> consulta, String[] campos) {
		return this.resultSetToObject(classe, consulta, campos, false, true);
	}

	/**
	 * @param classe
	 *            - Tipo da classe que será retornada
	 * @param consulta
	 *            - ResultSet com o retorno da consulta
	 * @param campos
	 *            - Campos que serão setados para o objeto de retorno.
	 * @return Retorna os objetos instanciados para o resultSet
	 * @author Rodrigo.Matos
	 */
	protected List resultSetToList(Class<?> classe, List<Object[]> consulta, String[] campos) {
		return this.resultSetToList(classe, consulta, campos, true, true);
	}

	/**
	 * @param classe
	 *            - Tipo da classe que será retornada
	 * @param consulta
	 *            - ResultSet com o retorno da consulta
	 * @param campos
	 *            - Campos que serão setados para o objeto de retorno.
	 * @return Retorna os objetos instanciados caso o valor do resultSet não seja
	 *         nulo
	 * @author Rodrigo.Matos
	 */
	protected List resultSetToListWithoutInstance(Class<?> classe, List<Object[]> consulta, String[] campos) {
		return this.resultSetToList(classe, consulta, campos, false, true);
	}

	protected Object resultSetToObjectWithoutInstanceAll(Class<?> classe, List<Object[]> consulta, String[] campos) {
		return this.resultSetToObject(classe, consulta, campos, false, false);
	}

	private Object resultSetToObject(Class<?> classe, List<Object[]> consulta, String[] campos, boolean ignorarNull,
			boolean ignoreInstanceNull) {
		List resultado = this.resultSetToList(classe, consulta, campos, ignorarNull, ignoreInstanceNull);
		if (!resultado.isEmpty()) {
			return resultado.get(0);
		} else {
			return null;
		}
	}

	protected List resultSetToListOfListWithoutInstance(Class<?>[] classs, Object[] camposClass, String[] attributList,
			List<Object[]> consulta) {
		return resultSetToListOfList(classs, camposClass, attributList, consulta, false);
	}

	/**
	 * @param classs
	 *            - Array das classes(pai,filho ...)
	 * @param camposClass
	 *            - Array de Arrays de String com os respectivos atributos das
	 *            classes listadas anteriormente
	 * @param attributList
	 *            - Array de Strings dos nosmes dos atributos list de cada classe
	 * @param consulta
	 *            - ResultSet com o retorno da consulta
	 * @return Retorna os objetos com cadas pai,filho ... preenchido para o
	 *         resultSet
	 */
	protected List resultSetToListOfList(Class<?>[] classs, Object[] camposClass, String[] attributList,
			List<Object[]> consulta) {
		return resultSetToListOfList(classs, camposClass, attributList, consulta, true);
	}

	private List resultSetToListOfList(Class<?>[] classs, Object[] camposClass, String[] attributList,
			List<Object[]> consulta, boolean withInstance) {
		List resultado = new ArrayList<>();
		List<Object[]> listToPassToMethod = new ArrayList<>();
		Integer index = 0;
		Object[] keys = new Object[classs.length];
		Object[] resgistroTemp = null;
		Object lastObjectAdded = null;
		for (Object[] registro : consulta) {
			listToPassToMethod.clear();
			listToPassToMethod.add(registro);
			Object objClass = null;
			index = 0;
			for (int i = 0; i < classs.length; i++) {
				try {
					if (keys[i] == null || (keys[i] != null && !keys[i].equals(registro[index]))
							|| i == classs.length - 1) {
						keys[i] = registro[index];
						for (int j = i + 1; j < keys.length; j++)
							keys[j] = null;
						if (index == 0) {
							objClass = withInstance
									? resultSetToObject(classs[i], listToPassToMethod, (String[]) camposClass[i])
									: resultSetToObjectWithoutInstance(classs[i], listToPassToMethod,
											(String[]) camposClass[i]);
							if (objClass != null) {
								resultado.add(objClass);
							}
						} else {
							resgistroTemp = Arrays.copyOfRange(registro, getPositionBegin(camposClass, i),
									getPositionEnd(camposClass, i));
							listToPassToMethod.clear();
							listToPassToMethod.add(resgistroTemp);
							lastObjectAdded = getLastObjectAdded(resultado, classs, attributList, i - 1);
							if (lastObjectAdded != null
									&& executarMetodoGet(lastObjectAdded, attributList[i - 1].toString()) == null) {
								this.executarMetodoSet(getLastObjectAdded(resultado, classs, attributList, i - 1),
										attributList[i - 1].toString(), new ArrayList<>(), true);
							}
							objClass = withInstance
									? resultSetToObject(classs[i], listToPassToMethod, (String[]) camposClass[i])
									: resultSetToObjectWithoutInstanceAll(classs[i], listToPassToMethod,
											(String[]) camposClass[i]);
							if (objClass != null) {
								((List) executarMetodoGet(getLastObjectAdded(resultado, classs, attributList, i - 1),
										attributList[i - 1].toString())).add(objClass);
							}
						}
					}
					index = getPositionBegin(camposClass, i + 1);
				} catch (Exception e) {
					throw new EnvironmentException(e);
				}
			}
		}
		return resultado;
	}

	private Object getLastObjectAdded(List resultado, Class<?>[] classs, String[] attributList, int indexLimit) {
		Object lastObjectAdded = resultado.get(resultado.size() - 1);
		for (int i = 0; i < indexLimit; i++) {
			Object obj = executarMetodoGet(lastObjectAdded, attributList[i]);
			if (obj != null) {
				if (((List) obj).isEmpty()) {
					return null;
				}
				lastObjectAdded = ((List) obj).get(((List) obj).size() - 1);
			} else
				break;
		}
		return lastObjectAdded;
	}

	private Integer getPositionBegin(Object[] list, int index) {
		int count = 0;
		for (int i = 0; i < list.length; i++) {
			if (i == index)
				break;
			count += ((String[]) list[i]).length;
		}
		return count;
	}

	private Integer getPositionEnd(Object[] list, int index) {
		int count = 0;
		for (int i = 0; i < list.length; i++) {
			count += ((String[]) list[i]).length;
			if (i > index)
				break;
		}
		return count;
	}

	private List resultSetToList(Class<?> classe, List<Object[]> consulta, String[] campos, boolean ignorarNull,
			boolean ignorarInstanceNull) {
		List resultado = new ArrayList<>();
		if (consulta != null && !consulta.isEmpty()) {
			Object objeto;
			boolean temDados = false;
			try {
				if (consulta.get(0) instanceof Object[]) {
					for (Object[] registro : consulta) {
						objeto = Class.forName(classe.getName()).newInstance();
						for (int i = 0; i < campos.length; i++) {
							if (campos[i] == null || "".equals(campos[i])) {
								continue;
							}
							if (this.executarMetodoSet(objeto, campos[i], registro[i], ignorarNull)) {
								temDados = true;
							}
						}
						if (ignorarInstanceNull || temDados) {
							resultado.add(objeto);
						} else {
							resultado.add(null);
						}
					}
				} else {
					for (Object registro : consulta) {
						objeto = Class.forName(classe.getName()).newInstance();
						this.executarMetodoSet(objeto, campos[0], registro, ignorarNull);
						resultado.add(objeto);
					}
				}
			} catch (Exception e) {
				throw new EnvironmentException(e);
			}
		}
		return resultado;
	}

	private boolean executarMetodoSet(Object objeto, String campo, Object registro, boolean ignorarNull) {
		if (ignorarNull || registro != null) {
			ReflexaoUtils.executarMetodoSet(objeto, campo, registro);
			return true;
		}
		return false;
	}

	private Object executarMetodoGet(Object objeto, String campo) {
		return ReflexaoUtils.executarMetodoGet(objeto, campo);
	}

	public Long getLong(Object object) {
		return ConvertType.getLong(object);
	}

	public Date getDate(Object object) {
		return ConvertType.getDate(object);
	}

	public Timestamp getTimestamp(Object object) {
		return ConvertType.getTimestamp(object);
	}

	public Integer getInteger(Object object) {
		return ConvertType.getInteger(object);
	}

	public Double getDouble(Object object) {
		return ConvertType.getDouble(object);
	}

	public Float getFloat(Object object) {
		return ConvertType.getFloat(object);
	}

	public BigDecimal getBigDecimal(Object object) {
		return ConvertType.getBigDecimal(object);
	}

	public String getString(Object object) {
		return ConvertType.getString(object);
	}

	public Boolean getBoolean(Object object) {
		return ConvertType.getBoolean(object);
	}

	public void setParameter(Query query, int indice, Object object) {
		if (object != null && object instanceof String && StringUtils.isBlank(object.toString())) {
			object = null;
		}
		query.setParameter(indice, (object != null) ? object : Types.NULL);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected void setPaginator(Query query, PaginatorCustom paginator) {
		query.setMaxResults(paginator.getPageSize());
		query.setFirstResult(paginator.getPageIndex() * paginator.getPageSize());
	}

	protected void setParameterMap(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> parameter : parameters.entrySet()) {
			query.setParameter(parameter.getKey(), parameter.getValue());
		}
	}

}
