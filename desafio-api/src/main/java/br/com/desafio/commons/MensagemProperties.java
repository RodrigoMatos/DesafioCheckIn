package br.com.desafio.commons;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@PropertySource("classpath:mensagem.properties")
@Getter
public class MensagemProperties {

	@Value("${sessaopublica.batepapo.mensagem.sistema}")
	private String nomeSistemaMensagem;

	@Value("${sessaopublica.batepapo.mensagem.responsavel}")
	private String mensagemBatePapoResponsavel;

	@Value("${sessaopublica.batepapo.mensagem.entrada}")
	private String mensagemBatePapoEntrada;

	@Value("${sessaopublica.batepapo.mensagem.saida}")
	private String mensagemBatePapoSaida;

	@Value("${sessaopublica.mensagem.licitacao.nao.encontrada}")
	private String licitacaoNaoEncontrada;

	@Value("${sessaopublica.mensagem.reverato.cronometro.executando}")
	private String elementoLicitacaoEmAndamento;

	@Value("${sessaopublica.elemento.mensagem.propostas.naoclassificadas}")
	private String propostasNaoClassificadas;

	@Value("${sessaopublica.elemento.mensagem.revogado}")
	private String mensagemElementoLicitacaoRevogado;

	@Value("${sessaopublica.elementolicitacao.finalizado}")
	private String mensagemElementoLicitacaoFinalizadoComConsorcio;

	@Value("${sessaopublica.elemento.mensagem.melhoroferta}")
	private String mensagemLanceGanhador;

	@Value("${sessaopublica.elemento.mensagem.suspenso}")
	private String mensagemElementoLicitacaoSupenso;

	@Value("${sessaoPublica.mensagem.licitacaoSuspensa}")
	private String mensagemLicitacaoSupenso;

	@Value("${sessaopublica.elemento.mensagem.retomado}")
	private String mensagemElementoLicitacaoRetormado;

	@Value("${sessaopublica.elemento.mensagem.preferencia}")
	private String mensagemElementoLicitacaoPreferencia;

	@Value("${sessaopublica.elemento.mensagem.negociacao}")
	private String mensagemElementoLicitacaoNegociacao;

	@Value("${sessaopublica.mensagem.elemento.disputa}")
	private String mensagemElementoLicitacaoEmDisputa;

	@Value("${sessaopublica.mensagem.elemento.varios.disputa}")
	private String mensagemElementoLicitacaoVariosEmDisputa;

	@Value("${sessaopublica.mensagem.naoexistem.propostas.cadastradas}")
	private String mensagemPropostasNaoCadastradas;

	@Value("${sessaopublica.mensagem.fase.elemento.andamento}")
	private String mensagemElementoLicitacaoAndamento;

	@Value("${sessaopublica.mensagem.lance.sempreferencia}")
	private String mensagemLanceSemPreferencia;

	@Value("${sessaopublica.elemento.mensagem.participante.nao.negociacao}")
	private String mensagemLanceSemNegociacao;

	@Value("${sessaopublica.mensagem.lancemenorzero}")
	private String mensagemLanceZero;

	@Value("${sessaopublica.mensagem.lance.cronometro.parado}")
	private String mensagemLanceCronometroParado;

	@Value("${sessaopublica.mensagem.lance.campos.obrigatorios}")
	private String mensagemLanceCamposObrigatorios;

	@Value("${sessaopublica.mensagem.lance.participante.naolocalizado}")
	private String mensagemLanceParticipaneteNaoLocalizado;

	@Value("${sessaopublica.mensagem.lance.elemento.naolocalizado}")
	private String mensagemLanceElementoNaoLocalizado;

	@Value("${sessaopublica.mensagem.lance.participante.preferencia.menoroferta}")
	private String mensagemLanceParticipanteMenorOferta;

	@Value("${sessaopublica.mensagem.lance.participante.atual.reducao.maxima}")
	private String mensagemLanceParticipanteReducaoMaxima;

	@Value("${sessaopublica.mensagem.lance.participante.oferta}")
	private String mensagemLanceParticipante;

	@Value("${sessaopublica.mensagem.lance.participante.negociacao.preferencia}")
	private String mensagemLanceParticipanteNeogicacao;

	@Value("${sessaopublica.mensagem.error}")
	private String mensagemError;

	@Value("${sessaoPublica.mensagem.elemento.empateficto}")
	private String mensagemElementoLicitacaoEmpateFicto;

	@Value("${sessaopublica.batepapo.mensagem.aberturapropostas}")
	private String mensagemAberturaProposta;

	@Value("${sessaopublica.mensagem.fase.elemento.andamento}")
	private String mensagemElementoLicitacaoAndamentoError;

	@Value("${sessaopublica.mensagem.lance.participante.melhoroferta.excluir.empty}")
	private String mensagemLanceParticipanteMelhorOfertaNaoEncontrado;

	@Value("${sessaopublica.mensagem.lance.participante.melhoroferta.more}")
	private String mensagemLanceParticipanteVariosEmMelhorOferta;

	@Value("${sessaopublica.mensagem.lance.participante.exclusao}")
	private String mensagemLanceParticipanteExclusao;

	@Value("${sessaopublica.mensagem.acessonegado}")
	private String mensagemAcessoNegado;

	@Value("${sessaopublica.mensagem.lance.participante.empty}")
	private String mensagemParticipanteNaoLocalizado;

	@Value("Participante não faz parte dessa licitação")
	private String mensagemNãoParticipante;

	@Value("${sessaopublica.mensagem.licitacao.naoencontrada}")
	private String mensagemLicitacaoNaoEncontrada;

	@Value("${sessaopublica.mensagem.licitacao.nao.finalizada}")
	private String mensagemLicitacaoNaoFinalizada;

	@Value("${sessaopublica.mensagem.habilitacao.participantes.campos.obrigatorios}")
	private String mensagemHabilitacaoParticipantesCamposObrigatorios;

	@Value("${sessaopublica.mensagem.habilitacao.elementos.disputa}")
	private String mensagemHabilitacaoElementosEmDisputa;

	@Value("${sessaopublica.mensagem.habilitacao.elemento}")
	private String mensagemHabilitacaoElemento;

	@Value("${sessaopublica.mensagem.habilitacao.elemento.participante}")
	private String mensagemHabilitacaoElementoParticipante;

	@Value("${sessaopublica.batepapo.mensagem.divulgacaopropostas}")
	private String mensagemDivulgacaoProspostas;

	@Value("${sessaopublica.batepapo.mensagem.reavaliacaopropostas}")
	private String mensagemReavalicaoPropostas;

	@Value("${sessaoPublica.mensagem.empateFicto}")
	private String mensagemEmpateFicito;

	@Value("${sessaopublica.mensagem.habilitacao.iniciada}")
	private String mensagemFaseHabilitacaoIniciada;

	@Value("${sessaopublica.mensagem.precoinaceitavel}")
	private String mensagemPrecoInaceitavel;

	@Value("${sessaopublica.mensagem.precoinaceitavel.error}")
	private String mensagemPrecoInaceitavelError;

	@Value("${sessaopublica.mensagem.reprovacao.amostra}")
	private String mensagemReprovacaoAmostra;

	@Value("${sessaoPublica.mensagem.interposicao.recurso.iniciada}")
	private String mensagemInterposicaoRecusoIniciada;

	@Value("${sessaoPublica.mensagem.interposicao.recurso.encerrada}")
	private String mensagemInterposicaoRecusoEncerrada;

	@Value("${sessaoPublica.mensagem.interposicao.recurso.motivo}")
	private String mensagemInterposicaoRecusoMotivo;

	@Value("${sessaopublica.elemento.mensagem.item.aberto.reducao}")
	private String mensagemElementoLicitacaoAbertoComReducao;

	@Value("${sessaopublica.elemento.mensagem.item.reducao.nao.permitido}")
	private String mensagemElementoLicitacaoReducaoNaoPermitida;

	@Value("${sessaopublica.batepapo.mensagem.reverato}")
	private String mensagemReverAto;

	@Value("${sessaopublica.batepapo.mensagem.reverato.classificacao}")
	private String mensagemReverAtoClassificacao;

	@Value("${sessaopublica.batepapo.mensagem.reverato.avaliacao}")
	private String mensagemReverAtoAvaliacao;

	@Value("${sessaopublica.batepapo.mensagem.reverato.habilitacao}")
	private String mensagemReverAtoHabilitacao;

	@Value("${sessaoPublica.mensagem.recomendado.rateio}")
	private String mensagemRecomendadoRateio;

	@Value("${sessaoPublica.mensagem.fatosuperveniente}")
	private String mensagemFatoSuperveniente;

	@Value("${sessaoPublica.mensagem.fatosuperveniente.semrateio}")
	private String mensagemFatoSupervenienteSem;

	@Value("${sessaoPublica.mensagem.fatosuperveniente.comrateio}")
	private String mensagemFatoSupervenienteComRateio;

	@Value("${download.edital.mensagem.fornecedor.nao.baixou}")
	private String mensagemNaoBaixouEdital;

	@Value("${sessaopublica.mensagem.encerrado}")
	private String mensagemLicitacaoEncerrada;

	@Value("${sessaopublica.mensagem.lance.naopermitido}")
	private String mensagemLanceNaoPermitido;

	@Value("sessaopublica.mensagem.lance.percentual.reducao.parametros.inconsistentes")
	private String mensagemReducaoPercentualInconsistentes;

	@Value("${sistema.mensagem.parametronecessario}")
	private String mensagemParametrosNecessarios;

	@Value("${sistema.mensagem.valordeveserinformado}")
	private String mensagemCampoDeveTerUmValorValido;

	@Value("O recurso para o item %s foi %s pelo motivo: %s")
	private String mensagemJulgamentoRecuso;

	public String getMensagemCampoDeveTerUmValorValido(String campo) {
		return String.format(mensagemCampoDeveTerUmValorValido, campo);
	}

}
