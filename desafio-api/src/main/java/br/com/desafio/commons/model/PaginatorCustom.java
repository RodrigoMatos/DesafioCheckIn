package br.com.desafio.commons.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginatorCustom implements Serializable {

	private static final long serialVersionUID = -3358558253252798358L;

	private Integer pageIndex;
	private Integer pageSize;
	private Integer length;

}
