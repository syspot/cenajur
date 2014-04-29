package br.com.cenajur.util;

import java.util.Date;
import java.util.List;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AgendaColaborador;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.ContadorEmail;
import br.com.cenajur.model.LogEnvioEmail;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.TipoAndamentoProcesso;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.model.Vara;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

public class EmailLayoutUtil {

	public void enviarEmailAndamentoProcesso(AndamentoProcesso andamento) throws TSApplicationException {

		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_ANDAMENTO_PROCESSO).getById();

		EmailUtil emailUtil = new EmailUtil();

		Processo processo = andamento.getProcessoNumero().getProcesso().getById();

		ConfiguracoesReplaceEmail configuracaoReplace;

		for (ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()) {

			if (configuracoesEmail.getFlagImediato()) {

				for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

					if (!TSUtil.isEmpty(processoCliente.getCliente().getEmail())) {

						StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());

						corpoEmail.append(configuracoesEmail.getCorpoEmail());

						corpoEmail.append(CenajurUtil.getRodapeEmail());

						String texto = corpoEmail.toString();

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), new Vara(processo.getVara().getId()).getById().getDescricao());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), processo.getAdvogado().getApelido());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_TIPO_ANDAMENTO).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), new TipoAndamentoProcesso(andamento.getTipoAndamentoProcesso().getId())
								.getById().getDescricao());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DESCRICAO).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), andamento.getDescricao());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), processoCliente.getCliente().getNome());

						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL).getById();

						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM));

						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						new ContadorEmail().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID));
						new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();

					}

				}

			}

		}

	}

	public void enviarEmailAudiencia(Audiencia audiencia) throws TSApplicationException {

		RegrasEmail regrasEmail = null;

		if (Constantes.SITUACAO_AUDIENCIA_AGUARDANDO.equals(audiencia.getSituacaoAudiencia().getId())) {

			regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA_SITUACAO_AGUARDANDO).getById();

		} else if (Constantes.SITUACAO_AUDIENCIA_REALIZADA.equals(audiencia.getSituacaoAudiencia().getId())) {

			regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA_SITUACAO_REALIZADA).getById();

		} else if (Constantes.SITUACAO_AUDIENCIA_NAO_REALIZADA.equals(audiencia.getSituacaoAudiencia().getId())) {

			regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA_SITUACAO_NAO_REALIZADA).getById();

		}

		EmailUtil emailUtil = new EmailUtil();

		ConfiguracoesReplaceEmail configuracaoReplace;

		Processo processo = audiencia.getProcessoNumero().getProcesso().getById();

		List<ConfiguracoesReplaceEmail> configuracoesReplaceEmails = new ConfiguracoesReplaceEmail().findAll();

		for (ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()) {

			if (configuracoesEmail.getFlagImediato()) {

				String texto = CenajurUtil.getTopoEmail() + configuracoesEmail.getCorpoEmail();

				configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
						Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO)));
				texto = texto.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());

				configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
						Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO)));
				texto = texto.replace(configuracaoReplace.getCodigo(),
						audiencia.getAudienciasAdvogados().toString().substring(1, audiencia.getAudienciasAdvogados().toString().length() - 1));

				configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
						Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA)));
				texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));

				configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
						Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA)));
				texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));

				configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
						Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL)));
				texto = texto.replace(configuracaoReplace.getCodigo(), audiencia.getVara().getById().getDescricao());

				for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

					configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
							Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO)));
					String email = texto.replace(configuracaoReplace.getCodigo(), processoCliente.getCliente().getNome()) + CenajurUtil.getRodapeEmail();

					if (!TSUtil.isEmpty(processoCliente.getCliente().getEmail())) {

						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), email, "text/html");
						new ContadorEmail().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID));
						new LogEnvioEmail(configuracoesEmail.getAssunto(), email, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();

					}

				}

				Colaborador advogado = null;

				for (AudienciaAdvogado audienciaAdvogado : audiencia.getAudienciasAdvogados()) {

					advogado = audienciaAdvogado.getAdvogado().getById();

					configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(
							Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO)));
					String email = texto.replace(configuracaoReplace.getCodigo(), advogado.getApelido()) + CenajurUtil.getRodapeEmail();

					if (!TSUtil.isEmpty(advogado.getEmail())) {

						emailUtil.enviarEmailTratado(advogado.getEmail(), configuracoesEmail.getAssunto(), email, "text/html");

					}

				}

			}

		}

	}

	public void enviarEmailVisita(AgendaColaborador agendaColaborador) throws TSApplicationException {

		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_VISITAS_ID);

		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_VISITA_COM_CLIENTE).getById();

		EmailUtil emailUtil = new EmailUtil();

		ConfiguracoesReplaceEmail configuracaoReplace;

		Agenda agenda = agendaColaborador.getAgenda().getById();

		for (ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()) {

			if (configuracoesEmail.getFlagImediato()) {

				if (!TSUtil.isEmpty(agenda.getCliente().getEmail())) {

					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());

					corpoEmail.append(configuracoesEmail.getCorpoEmail());

					corpoEmail.append(CenajurUtil.getRodapeEmail());

					String texto = corpoEmail.toString();

					configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO).getById();

					texto = texto.replace(configuracaoReplace.getCodigo(), agenda.getCliente().getNome());

					configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL).getById();

					texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM));

					configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_VISITA).getById();

					texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(agenda.getDataInicial(), TSDateUtil.DD_MM_YYYY_HH_MM));

					configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();

					texto = texto.replace(configuracaoReplace.getCodigo(), agendaColaborador.getColaborador().getApelido());

					emailUtil.enviarEmailTratado(agenda.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, agenda.getCliente(), agenda.getCliente().getEmail()).save();

				}

			}

		}

	}

	public void enviarEmailAssociadoNovo(Cliente cliente) throws TSApplicationException {

		if (!TSUtil.isEmpty(cliente.getEmail())) {

			if (!TSUtil.isEmpty(cliente.getEmail())) {

				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());

				corpoEmail.append("Prezado(a) ");
				corpoEmail.append(cliente.getNome());
				corpoEmail
						.append("<br/><br/>Obrigado por escolher o CENAJUR, aproveitamos a oportunidade e reafirmamos nosso compromisso de prestar uma assistência jurídica efetiva e com qualidade, ética, responsabilidade e experiência.");
				corpoEmail
						.append("<br/><br/>Para verificar as informações dos seus processos e audiências, além de notícias atualizadas da Associação, acesse www.cenajur.com.br com sua matrícula ");
				corpoEmail.append(cliente.getMatricula());
				corpoEmail.append(" e sua senha que é seu CPF");

				corpoEmail.append(CenajurUtil.getRodapeEmail());

				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ASSOCIADOS_NOVOS_ID);

				new EmailUtil().enviarEmailTratado(cliente.getEmail(), "CENAJUR AGRADECE", corpoEmail.toString(), "text/html");
				new ContadorEmail().gravarPorTipo(tipoInformacao);
				new LogEnvioEmail("CENAJUR AGRADECE", corpoEmail.toString(), cliente, cliente.getEmail()).save();

			}

		}

	}
}
