package br.com.cenajur.jobs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.cenajur.model.Agenda;
import br.com.cenajur.model.AndamentoProcesso;
import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.ConfiguracoesEmail;
import br.com.cenajur.model.ConfiguracoesReplaceEmail;
import br.com.cenajur.model.ContadorEmail;
import br.com.cenajur.model.ContadorSms;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;


public class EnviarEmailJob {

	public void processarEnvioEmail(){
		
		EmailUtil emailUtil = new EmailUtil();
		
		this.enviarEmailAudiencia(emailUtil);
		
		this.enviarEmailAndamento(emailUtil);
		
		this.enviarEmailNovosAssociados(emailUtil);
		
		this.enviarEmailAniversariantes(emailUtil);
		
		this.enviarEmailInadimplentes(emailUtil);
		
		this.enviarEmailProcessosNovos(emailUtil);
		
		this.enviarEmailVisitas(emailUtil);
		
	}
	
	private void enviarEmailAudiencia(EmailUtil emailUtil){
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA).getById();
		
		List<Audiencia> audiencias = new Audiencia().pesquisarAudienciasProximas(regrasEmail.getDiasEnvio());
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		Processo processo = null;
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(!configuracoesEmail.getFlagImediato()){
				
				for(Audiencia audiencia : audiencias){
					
					processo = audiencia.getProcessoNumero().getProcesso().getById();
					
					for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
						
						if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
							
							StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
							
							corpoEmail.append(configuracoesEmail.getCorpoEmail());
							
							corpoEmail.append(CenajurUtil.getRodapeEmail());
							
							String texto = corpoEmail.toString();
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO).getById();
							
							texto = texto.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();
							
							texto = texto.replace(configuracaoReplace.getCodigo(), audiencia.getAudienciasAdvogados().toString());
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA).getById();
							
							texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA).getById();
							
							texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL).getById();
							
							texto = texto.replace(configuracaoReplace.getCodigo(), audiencia.getVara().getDescricao());
								
							emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
							
							TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);
							
							new ContadorEmail().gravarPorTipo(tipoInformacao);
							new ContadorSms().gravarPorTipo(tipoInformacao);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private void enviarEmailAndamento(EmailUtil emailUtil){
		
		List<AndamentoProcesso> andamentos = new AndamentoProcesso().pesquisarAndamentoRecente();
		
		Processo processo = null;
		
		for(AndamentoProcesso andamentoProcesso : andamentos){
			
			processo = andamentoProcesso.getProcessoNumero().getProcesso().getById();
			
			for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
				
				if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					corpoEmail.append("Verifique o último andamento do seu processo no. "+ andamentoProcesso.getProcessoNumero().getNumeroFormatado() + ".  Acesse www.agepol.org.br");
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					String texto = corpoEmail.toString();
					
					emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), "Cenajur Informa", texto, "text/html");
					
					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID);
					
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new ContadorSms().gravarPorTipo(tipoInformacao);
					
				}
				
			}
					
		}
		
	}
	
	private void enviarEmailNovosAssociados(EmailUtil emailUtil){
		
		List<Cliente> clientes = new Cliente().pesquisarNovosAssociados();
		
		for(Cliente cliente : clientes){
			
			if(!TSUtil.isEmpty(cliente.getEmail())){
				
				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
				
				corpoEmail.append("Seja bem vindo a AGEPOL/CENAJUR. Sua matricula e: " + cliente.getMatricula() + "  e sua senha é seu CPF. Acesse www.agepol.org.br");
				
				corpoEmail.append(CenajurUtil.getRodapeEmail());
				
				emailUtil.enviarEmailTratado(cliente.getEmail(), "Boas Vindas", corpoEmail.toString(), "text/html");
				
				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ASSOCIADOS_NOVOS_ID);
				
				new ContadorEmail().gravarPorTipo(tipoInformacao);
				new ContadorSms().gravarPorTipo(tipoInformacao);
				
			}
				
		}
				
	}
	
	private void enviarEmailAniversariantes(EmailUtil emailUtil){
		
		List<Cliente> clientes = new Cliente().pesquisarAniversariantes();
		
		for(Cliente cliente : clientes){
			
			if(!TSUtil.isEmpty(cliente.getEmail())){
				
				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
				
				corpoEmail.append("Desejamos um feliz aniversario neste dia tão especial.");
				
				corpoEmail.append(CenajurUtil.getRodapeEmail());
				
				emailUtil.enviarEmailTratado(cliente.getEmail(), "Cenajur Informa", corpoEmail.toString(), "text/html");
				
				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANIVERSARIO_ID);
				
				new ContadorEmail().gravarPorTipo(tipoInformacao);
				new ContadorSms().gravarPorTipo(tipoInformacao);
				
			}
				
		}
				
	}
	
	private void enviarEmailInadimplentes(EmailUtil emailUtil){
		
		Calendar data = Calendar.getInstance();
		
		if(data.get(Calendar.DAY_OF_MONTH) == 10){
			
			List<Cliente> clientes = new Cliente().pesquisarInadimplentes(data.get(Calendar.MONTH), data.get(Calendar.YEAR));
			
			for(Cliente cliente : clientes){
				
				if(!TSUtil.isEmpty(cliente.getEmail())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					corpoEmail.append("Prezado(a), até o momento não consta o pagamento do último mês. Favor contactar o CENAJUR. Caso já tenha efetuado, desconsidere.");
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					emailUtil.enviarEmailTratado(cliente.getEmail(), "Cenajur Informa", corpoEmail.toString(), "text/html");
					
					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_INADIMPLENCIA_ID);
					
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new ContadorSms().gravarPorTipo(tipoInformacao);
					
				}
				
			}
		
		}
				
	}
	
	private void enviarEmailProcessosNovos(EmailUtil emailUtil){
		
		List<ProcessoNumero> processosNumeros = new ProcessoNumero().pesquisarProcessosNovos();
		
		for(ProcessoNumero processoNumero : processosNumeros){
			
			for(ProcessoCliente processoCliente : processoNumero.getProcesso().getProcessosClientes()){
				
				if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					corpoEmail.append("Seu processo do(a) " + processoNumero.getProcesso().getObjeto().getDescricao() + ", está cadastrado com número " + processoNumero.getNumeroFormatado() + ". Informações acesse www.agepol.org.br");
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), "Cenajur Informa", corpoEmail.toString(), "text/html");
					
					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_PROCESSO_NOVO_ID);
					
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new ContadorSms().gravarPorTipo(tipoInformacao);
					
				}
				
			}
			
		}
				
	}
	
	private void enviarEmailVisitas(EmailUtil emailUtil){
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_VISITA_COM_CLIENTE).getById();
		
		List<Agenda> visitas = new Agenda().pesquisarVisitasProximas(regrasEmail.getDiasEnvio());
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(!configuracoesEmail.getFlagImediato()){
				
				for(Agenda visita : visitas){
					
					if(!TSUtil.isEmpty(visita.getCliente().getEmail())){
						
						String texto = configuracoesEmail.getCorpoEmail();
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), visita.getCliente().getNome());
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM));
							
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_VISITA).getById();
						
						texto = texto.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(visita.getDataInicial(), TSDateUtil.DD_MM_YYYY_HH_MM));
						
						emailUtil.enviarEmailTratado(visita.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
						
						TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_VISITAS_ID);
						
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						new ContadorSms().gravarPorTipo(tipoInformacao);
						
					}
						
				}
				
			}
			
		}
		
	}
	
}
