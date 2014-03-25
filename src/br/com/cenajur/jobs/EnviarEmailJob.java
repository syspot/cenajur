package br.com.cenajur.jobs;

import java.util.Calendar;
import java.util.List;

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.ContadorEmail;
import br.com.cenajur.model.ContadorSms;
import br.com.cenajur.model.LogEnvioEmail;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.cenajur.util.SMSUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

public class EnviarEmailJob {

	private static String ASSUNTO_GERAL = "CENAJUR INFORMA";

	public void processarEnvioEmail() {

		EmailUtil emailUtil = new EmailUtil();
		SMSUtil smsUtil = new SMSUtil();

		try {

			this.enviarSMSAudienciaSituacaoAguardando(smsUtil);

			this.enviarSMSAniversariantes(smsUtil);

			this.enviarSMSInadimplentes(smsUtil);
			
			this.enviarEmailAudienciaAguardando(emailUtil);
			
			this.enviarEmailAniversariantes(emailUtil);
			
			this.enviarEmailInadimplentes(emailUtil);
			
		} catch (TSApplicationException e) {
			
			e.printStackTrace();
			
		}

	}

	private void enviarEmailAudienciaAguardando(EmailUtil emailUtil) throws TSApplicationException {

		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA_SITUACAO_AGUARDANDO).getById();
		
		List<Audiencia> audiencias = new Audiencia().pesquisarAudienciasProximas(regrasEmail.getDiasEnvio());

		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);

		ConfiguracoesReplaceEmail configuracaoReplace;

		Processo processo = null;
		
		List<ConfiguracoesReplaceEmail> configuracoesReplaceEmails = new ConfiguracoesReplaceEmail().findAll();

		for (Audiencia audiencia : audiencias) {
			
			for (ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()) {

				if(Constantes.SITUACAO_AUDIENCIA_AGUARDANDO.equals(audiencia.getSituacaoAudiencia().getId())){
					
					if (!configuracoesEmail.getFlagImediato()) {
						
						processo = audiencia.getProcessoNumero().getProcesso().getById();
						
						String textoLimpo = configuracoesEmail.getCorpoEmail();
						
						configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO)));
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
						
						configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO)));
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), audiencia.getAudienciasAdvogados().toString().substring(1, audiencia.getAudienciasAdvogados().toString().length()-1));

						configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA)));
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));

						configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA)));
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
						
						configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL)));
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), audiencia.getVara().getDescricao());
						
						for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {
						
							configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO)));

							String email = textoLimpo.replace(configuracaoReplace.getCodigo(), processoCliente.getCliente().getNome()) + CenajurUtil.getRodapeEmail();
							
							if (!TSUtil.isEmpty(processoCliente.getCliente().getEmail())) {
								
								emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), email, "text/html");
								new ContadorEmail().gravarPorTipo(tipoInformacao);
								new LogEnvioEmail(configuracoesEmail.getAssunto(), email, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
								
							}
								
						}
						
						Colaborador advogado = null;
						
						for (AudienciaAdvogado audienciaAdvogado : audiencia.getAudienciasAdvogados()) {

							advogado = audienciaAdvogado.getAdvogado().getById();
							
							configuracaoReplace = configuracoesReplaceEmails.get(configuracoesReplaceEmails.indexOf(new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO)));
							
							String email = textoLimpo.replace(configuracaoReplace.getCodigo(), advogado.getApelido()) + CenajurUtil.getRodapeEmail();
							
							if (!TSUtil.isEmpty(advogado.getEmail())) {
								
								emailUtil.enviarEmailTratado(advogado.getEmail(), configuracoesEmail.getAssunto(), email, "text/html");
								
							}
							
						}
						
					}
					
				}

			}

		}

	}

	private void enviarSMSAudienciaSituacaoAguardando(SMSUtil smsUtil) throws TSApplicationException {

		List<Audiencia> audiencias = new Audiencia().pesquisarAudienciasProximas(1);

		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);

		Processo processo = null;

		for (Audiencia audiencia : audiencias) {

			processo = audiencia.getProcessoNumero().getProcesso().getById();

			String msg = Constantes.TEMPLATE_SMS_AUDIENCIA_AGUARDANDO;

			msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_NUMERO_PROCESSO, new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
			msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_DATA, TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
			msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_HORA, TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
			msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_LOCAL, audiencia.getVara().getDescricao());
			msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_COLABORADOR, audiencia.getAudienciasAdvogados().toString().substring(1, audiencia.getAudienciasAdvogados().toString().length() - 1));

			for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

				if (!TSUtil.isEmpty(processoCliente.getCliente().getCelular())
						&& Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO.equals(processoCliente.getSituacaoProcessoCliente().getId())) {

					smsUtil.enviarMensagem(processoCliente.getCliente().getCelular(), msg.toString());
					new ContadorSms().gravarPorTipo(tipoInformacao);

				}

			}

			Colaborador advogado = null;

			for (AudienciaAdvogado audienciaAdvogado : audiencia.getAudienciasAdvogados()) {

				advogado = audienciaAdvogado.getAdvogado().getById();

				if (!TSUtil.isEmpty(advogado.getCelular())) {

					smsUtil.enviarMensagem(advogado.getCelular(), msg.toString());
					new ContadorSms().gravarPorTipo(tipoInformacao);

				}

			}

		}

	}

	private void enviarEmailAniversariantes(EmailUtil emailUtil) throws TSApplicationException {

		List<Cliente> clientes = new Cliente().pesquisarAniversariantes();

		for (Cliente cliente : clientes) {

			if (!TSUtil.isEmpty(cliente.getEmail())) {

				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());

				corpoEmail.append("Prezado(a) ");
				corpoEmail.append(cliente.getNome());
				corpoEmail.append("<br/><br/>A equipe do CENAJUR deseja SAÚDE, PAZ e cada vez mais SEGURANÇA JURÍDICA. Feliz Aniversário!");

				corpoEmail.append(CenajurUtil.getRodapeEmail());

				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANIVERSARIO_ID);

				emailUtil.enviarEmailTratado(cliente.getEmail(), "CENAJUR DESEJA UM FELIZ ANIVERSÁRIO", corpoEmail.toString(), "text/html");
				new ContadorEmail().gravarPorTipo(tipoInformacao);
				new LogEnvioEmail(ASSUNTO_GERAL, corpoEmail.toString(), cliente, cliente.getEmail()).save();

			}

		}

	}

	private void enviarSMSAniversariantes(SMSUtil smsUtil) throws TSApplicationException {

		List<Cliente> clientes = new Cliente().pesquisarAniversariantes();

		for (Cliente cliente : clientes) {

			if (!TSUtil.isEmpty(cliente.getCelular())) {

				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANIVERSARIO_ID);

				smsUtil.enviarMensagem(cliente.getCelular(), Constantes.TEMPLATE_SMS_ANIVERSARIANTE);
				new ContadorSms().gravarPorTipo(tipoInformacao);

			}

		}

	}

	private void enviarEmailInadimplentes(EmailUtil emailUtil) throws TSApplicationException {

		Calendar data = Calendar.getInstance();

		if (data.get(Calendar.DAY_OF_MONTH) == 22) {

			List<Cliente> clientes = new Cliente().pesquisarInadimplentes(data.get(Calendar.MONTH), data.get(Calendar.YEAR));

			for (Cliente cliente : clientes) {

				if (!TSUtil.isEmpty(cliente.getEmail())) {

					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());

					corpoEmail.append("Prezado(a) ");
					corpoEmail.append(cliente.getNome());
					corpoEmail.append(", <br/><br/>Até o momento não consta o pagamento do ultimo mês. Favor Contactar a Associação. Caso já tenha efetuado o pagamento, desconsidere esta mensagem.");

					corpoEmail.append(CenajurUtil.getRodapeEmail());

					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_INADIMPLENCIA_ID);

					emailUtil.enviarEmailTratado(cliente.getEmail(), ASSUNTO_GERAL, corpoEmail.toString(), "text/html");
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new LogEnvioEmail(ASSUNTO_GERAL, corpoEmail.toString(), cliente, cliente.getEmail()).save();

				}

			}

		}

	}

	private void enviarSMSInadimplentes(SMSUtil smsUtil) throws TSApplicationException {

		Calendar data = Calendar.getInstance();

		if (data.get(Calendar.DAY_OF_MONTH) == 22) {

			List<Cliente> clientes = new Cliente().pesquisarInadimplentes(data.get(Calendar.MONTH), data.get(Calendar.YEAR));

			for (Cliente cliente : clientes) {

				if (!TSUtil.isEmpty(cliente.getCelular())) {

					String msg = Constantes.TEMPLATE_SMS_INADIMPLENTES;

					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_INADIMPLENCIA_ID);

					smsUtil.enviarMensagem(cliente.getCelular(), msg);
					new ContadorSms().gravarPorTipo(tipoInformacao);

				}

			}

		}

	}

}
