package br.com.desafio.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.desafio.commons.convert.BooleanConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HOSPEDES")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "codigo")
@AllArgsConstructor
@NoArgsConstructor
public class HospedeEntity implements Serializable {

	private static final long serialVersionUID = 8187589629687756486L;

	@Id
	@Column(name = "ID_HOSPEDE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospede_sequence")
	@SequenceGenerator(name = "hospede_sequence", sequenceName = "SEQ_HOSPEDE", allocationSize = 1)
	private Long codigo;

	@NotBlank
	@Size(max = 50)
	@Column(name = "DS_NOME")
	private String nome;

	@NotBlank
	@Size(max = 15)
	@Column(name = "DS_DOCUMENTO")
	private String documento;

	@NotNull
	@Size(max = 11)
	@Column(name = "DS_TELEFONE")
	private String telefone;
	
	@Transient
	private BigDecimal totalGasto;
	
	@Transient
	private BigDecimal totalUltimoCheckIn;

}
