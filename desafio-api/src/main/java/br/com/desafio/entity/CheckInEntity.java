package br.com.desafio.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.desafio.commons.convert.BooleanConverter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CHECKIN")
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "codigo")
@AllArgsConstructor
@NoArgsConstructor
public class CheckInEntity implements Serializable {

	private static final long serialVersionUID = 8187589629687756486L;

	@Id
	@Column(name = "ID_CHECKIN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "checkin_sequence")
	@SequenceGenerator(name = "checkin_sequence", sequenceName = "SEQ_CHECKIN", allocationSize = 1)
	private Long codigo;

	@NotNull
	@JoinColumn(name = "ID_HOSPEDE_FK", updatable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private HospedeEntity hospede;

	@NotNull
	@Column(name = "DT_INICIO")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd  hh24:mm:ss")
	private Date dataInicio;

	@NotNull
	@Column(name = "DT_FIM")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd hh24:mm:ss")
	private Date dataFim;

	@NotNull
	@Digits(integer = 8, fraction = 2)
	@Column(name = "VL_VALOR")
	private BigDecimal valor;

	@NotNull
	@Convert(converter = BooleanConverter.class)
	@Column(name = "FL_ADICIONAL_VEIC", columnDefinition = "char")
	private Boolean adicinalVeiculo;

}
