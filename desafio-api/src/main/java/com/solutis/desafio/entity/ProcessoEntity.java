package com.solutis.desafio.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PROCESSO")
public class ProcessoEntity implements Serializable {

	private static final long serialVersionUID = 8187589629687756486L;

	@Id
	@Column(name = "ID_PROCESSO")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "processo_sequence")
	@SequenceGenerator(name = "processo_sequence", sequenceName = "SEQ_PROCESSO", allocationSize = 1)
	private Long codigo;

	@NotBlank
	@Size(max = 50)
	@Column(name = "DS_STRING")
	private String string;

	@NotNull
	@Column(name = "DS_VOGAL")
	private Character vogal;

	@NotNull
	@Column(name = "NUM_TEMPO_TOTAL")
	private Long tempoTotal;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Character getVogal() {
		return vogal;
	}

	public void setVogal(Character vogal) {
		this.vogal = vogal;
	}

	public Long getTempoTotal() {
		return tempoTotal;
	}

	public void setTempoTotal(Long tempoTotal) {
		this.tempoTotal = tempoTotal;
	}

}
