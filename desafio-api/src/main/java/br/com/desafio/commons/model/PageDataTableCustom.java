package br.com.desafio.commons.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class PageDataTableCustom extends PaginatorCustom {

	private static final long serialVersionUID = 5117341407083916932L;

	private List<?> listObject;

}
