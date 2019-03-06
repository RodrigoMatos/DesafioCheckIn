package br.com.desafio.entity.filter;

import br.com.desafio.commons.model.PaginatorCustom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HospedeFilter extends PaginatorCustom {

	private static final long serialVersionUID = -3498387186367449333L;

	private Long codigo;

	private String nome;

	private String documento;

	private String telefone;

	private Boolean somenteCheckIn;

	private Boolean somenteCheckOut;

}
