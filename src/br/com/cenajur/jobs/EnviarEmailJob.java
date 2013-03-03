package br.com.cenajur.jobs;

import java.net.URL;
import java.net.URLConnection;
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
import br.com.cenajur.model.LogEnvioEmail;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.RegrasEmail;
import br.com.cenajur.model.TipoInformacao;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.cenajur.util.EmailUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;


public class EnviarEmailJob {
	
	private static String ASSUNTO_GERAL = "Cenajur Informa";

	public void processarEnvioEmail(){
		
		EmailUtil emailUtil = new EmailUtil();
		
		try {
			
			this.enviarEmailAudiencia(emailUtil);
			
			this.enviarEmailAndamento(emailUtil);
			
			this.enviarEmailNovosAssociados(emailUtil);
			
			this.enviarEmailAniversariantes(emailUtil);
			
			this.enviarEmailInadimplentes(emailUtil);
			
			this.enviarEmailProcessosNovos(emailUtil);
			
			this.enviarEmailVisitas(emailUtil);
			
			//this.enviarMensagem2("557188992709", "Teste oficial pelo Java 2");
			
		} catch (TSApplicationException e) {
			e.printStackTrace();
		}
		
	}
	
	private void enviarEmailAudiencia(EmailUtil emailUtil) throws TSApplicationException{
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_AUDIENCIA).getById();
		
		List<Audiencia> audiencias = new Audiencia().pesquisarAudienciasProximas(regrasEmail.getDiasEnvio());
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID);
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		Processo processo = null;
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(!configuracoesEmail.getFlagImediato()){
				
				for(Audiencia audiencia : audiencias){
					
					processo = audiencia.getProcessoNumero().getProcesso().getById();
					
					for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
						
						if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail()) || !TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
							
							StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
							
							String textoLimpo = configuracoesEmail.getCorpoEmail();
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_PROCESSO).getById();
							
							textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ADVOGADO).getById();
							
							textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), audiencia.getAudienciasAdvogados().toString());
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA).getById();
							
							textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_HORA).getById();
							
							textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
							
							configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_LOCAL).getById();
							
							textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), audiencia.getVara().getDescricao());
							
							corpoEmail.append(textoLimpo);
							
							corpoEmail.append(CenajurUtil.getRodapeEmail());
							
							String texto = corpoEmail.toString();
							
							if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
								
								emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), configuracoesEmail.getAssunto(), texto, "text/html");
								new ContadorEmail().gravarPorTipo(tipoInformacao);
								new LogEnvioEmail(configuracoesEmail.getAssunto(), texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
								
							}
							
							if(!TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
								
//								this.enviarMensagem(processoCliente.getCliente().getCelular(), textoLimpo);
//								new ContadorSms().gravarPorTipo(tipoInformacao);
								
							}
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private void enviarEmailAndamento(EmailUtil emailUtil) throws TSApplicationException{
		
		List<AndamentoProcesso> andamentos = new AndamentoProcesso().pesquisarAndamentoRecente();
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID);
		
		Processo processo = null;
		
		for(AndamentoProcesso andamentoProcesso : andamentos){
			
			processo = andamentoProcesso.getProcessoNumero().getProcesso().getById();
			
			for(ProcessoCliente processoCliente : processo.getProcessosClientes()){
				
				if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail()) || !TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					String textoLimpo = "Verifique o último andamento do seu processo no. "+ andamentoProcesso.getProcessoNumero().getNumeroFormatado() + ".  Acesse www.agepol.org.br"; 
					
					corpoEmail.append(textoLimpo);
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					String texto = corpoEmail.toString();
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
						
						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), ASSUNTO_GERAL, texto, "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						new LogEnvioEmail(ASSUNTO_GERAL, texto, processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
						
					}
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
						
//						this.enviarMensagem(processoCliente.getCliente().getCelular(), textoLimpo);
//						new ContadorSms().gravarPorTipo(tipoInformacao);
						
					}
					
				}
				
			}
					
		}
		
	}
	
	private void enviarEmailNovosAssociados(EmailUtil emailUtil) throws TSApplicationException{
		
		List<Cliente> clientes = new Cliente().pesquisarNovosAssociados();
		
		String assunto = "Boas Vindas";
		
		for(Cliente cliente : clientes){
			
			if(!TSUtil.isEmpty(cliente.getEmail()) || !TSUtil.isEmpty(cliente.getCelular())){
				
				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
				
				String textoLimpo = "Seja bem vindo a AGEPOL/CENAJUR. Sua matricula e: " + cliente.getMatricula() + "  e sua senha é seu CPF. Acesse www.agepol.org.br";
				
				corpoEmail.append(textoLimpo);
				
				corpoEmail.append(CenajurUtil.getRodapeEmail());
				
				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ASSOCIADOS_NOVOS_ID);
				
				if(!TSUtil.isEmpty(cliente.getEmail())){
					
					emailUtil.enviarEmailTratado(cliente.getEmail(), assunto, corpoEmail.toString(), "text/html");
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new LogEnvioEmail(assunto, corpoEmail.toString(), cliente, cliente.getEmail()).save();
					
				}
				
				if(!TSUtil.isEmpty(cliente.getCelular())){
					
//					this.enviarMensagem(cliente.getCelular(), textoLimpo);
//					new ContadorSms().gravarPorTipo(tipoInformacao);
					
				}
				
			}
				
		}
				
	}
	
	private void enviarEmailAniversariantes(EmailUtil emailUtil) throws TSApplicationException{
		
		List<Cliente> clientes = new Cliente().pesquisarAniversariantes();
		
		for(Cliente cliente : clientes){
			
			if(!TSUtil.isEmpty(cliente.getEmail()) || !TSUtil.isEmpty(cliente.getCelular())){
				
				StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
				
				String textoLimpo = "Desejamos um feliz aniversario neste dia tão especial.";
				
				corpoEmail.append(textoLimpo);
				
				corpoEmail.append(CenajurUtil.getRodapeEmail());
				
				TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_ANIVERSARIO_ID);
				
				if(!TSUtil.isEmpty(cliente.getEmail())){
					
					emailUtil.enviarEmailTratado(cliente.getEmail(), ASSUNTO_GERAL, corpoEmail.toString(), "text/html");
					new ContadorEmail().gravarPorTipo(tipoInformacao);
					new LogEnvioEmail(ASSUNTO_GERAL, corpoEmail.toString(), cliente, cliente.getEmail()).save();
					
				}
				
				if(!TSUtil.isEmpty(cliente.getCelular())){
					
//					this.enviarMensagem(cliente.getCelular(), textoLimpo);
//					new ContadorSms().gravarPorTipo(tipoInformacao);
					
				}
				
			}
				
		}
				
	}
	
	private void enviarEmailInadimplentes(EmailUtil emailUtil) throws TSApplicationException{
		
		Calendar data = Calendar.getInstance();
		
		if(data.get(Calendar.DAY_OF_MONTH) == 10){
			
			List<Cliente> clientes = new Cliente().pesquisarInadimplentes(data.get(Calendar.MONTH), data.get(Calendar.YEAR));
			
			for(Cliente cliente : clientes){
				
				if(!TSUtil.isEmpty(cliente.getEmail()) || !TSUtil.isEmpty(cliente.getCelular())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					String textoLimpo = "Prezado(a), até o momento não consta o pagamento do último mês. Favor contactar o CENAJUR. Caso já tenha efetuado, desconsidere.";
					
					corpoEmail.append(textoLimpo);
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_INADIMPLENCIA_ID);
					
					if(!TSUtil.isEmpty(cliente.getEmail())){
						
						emailUtil.enviarEmailTratado(cliente.getEmail(), ASSUNTO_GERAL, corpoEmail.toString(), "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						new LogEnvioEmail(ASSUNTO_GERAL, corpoEmail.toString(), cliente, cliente.getEmail()).save();
						
					}
					
					if(!TSUtil.isEmpty(cliente.getCelular())){
						
//						this.enviarMensagem(cliente.getCelular(), textoLimpo);
//						new ContadorSms().gravarPorTipo(tipoInformacao);
						
					}
					
				}
				
			}
		
		}
				
	}
	
	private void enviarEmailProcessosNovos(EmailUtil emailUtil) throws TSApplicationException{
		
		List<ProcessoNumero> processosNumeros = new ProcessoNumero().pesquisarProcessosNovos();
		
		for(ProcessoNumero processoNumero : processosNumeros){
			
			for(ProcessoCliente processoCliente : processoNumero.getProcesso().getProcessosClientes()){
				
				if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail()) || !TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
					
					StringBuilder corpoEmail = new StringBuilder(CenajurUtil.getTopoEmail());
					
					String textoLimpo = "Seu processo do(a) " + processoNumero.getProcesso().getObjeto().getDescricao() + ", está cadastrado com número " + processoNumero.getNumeroFormatado() + ". Informações acesse www.agepol.org.br";
					
					corpoEmail.append(textoLimpo);
					
					corpoEmail.append(CenajurUtil.getRodapeEmail());
					
					TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_PROCESSO_NOVO_ID);
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getEmail())){
						
						emailUtil.enviarEmailTratado(processoCliente.getCliente().getEmail(), ASSUNTO_GERAL, corpoEmail.toString(), "text/html");
						new ContadorEmail().gravarPorTipo(tipoInformacao);
						new LogEnvioEmail(ASSUNTO_GERAL, corpoEmail.toString(), processoCliente.getCliente(), processoCliente.getCliente().getEmail()).save();
						
					}
					
					if(!TSUtil.isEmpty(processoCliente.getCliente().getCelular())){
						
//						this.enviarMensagem(processoCliente.getCliente().getCelular(), textoLimpo);
//						new ContadorSms().gravarPorTipo(tipoInformacao);
						
					}
					
				}
				
			}
			
		}
				
	}
	
	private void enviarEmailVisitas(EmailUtil emailUtil) throws TSApplicationException{
		
		ConfiguracoesReplaceEmail configuracaoReplace;
		
		TipoInformacao tipoInformacao = new TipoInformacao(Constantes.TIPO_INFORMACAO_VISITAS_ID);
		
		RegrasEmail regrasEmail = new RegrasEmail(Constantes.REGRA_EMAIL_VISITA_COM_CLIENTE).getById();
		
		List<Agenda> visitas = new Agenda().pesquisarVisitasProximas(regrasEmail.getDiasEnvio());
		
		for(ConfiguracoesEmail configuracoesEmail : regrasEmail.getConfiguracoesEmails()){
			
			if(!configuracoesEmail.getFlagImediato()){
				
				for(Agenda visita : visitas){
					
					if(!TSUtil.isEmpty(visita.getCliente().getEmail()) || !TSUtil.isEmpty(visita.getCliente().getCelular())){
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO).getById();
						
						String textoLimpo = configuracoesEmail.getCorpoEmail(); 
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), visita.getCliente().getNome());
						
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL).getById();
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(new Date(), TSDateUtil.DD_MM_YYYY_HH_MM));
							
						configuracaoReplace = new ConfiguracoesReplaceEmail(Constantes.CONFIGURACOES_REPLACE_EMAIL_DATA_VISITA).getById();
						
						textoLimpo = textoLimpo.replace(configuracaoReplace.getCodigo(), TSParseUtil.dateToString(visita.getDataInicial(), TSDateUtil.DD_MM_YYYY_HH_MM));
						
						if(!TSUtil.isEmpty(visita.getCliente().getEmail())){
							
							emailUtil.enviarEmailTratado(visita.getCliente().getEmail(), configuracoesEmail.getAssunto(), textoLimpo, "text/html");
							new ContadorEmail().gravarPorTipo(tipoInformacao);
							new LogEnvioEmail(configuracoesEmail.getAssunto(), textoLimpo, visita.getCliente(), visita.getCliente().getEmail()).save();
							
						}
						
						if(!TSUtil.isEmpty(visita.getCliente().getCelular())){
							
//							this.enviarMensagem(visita.getCliente().getCelular(), textoLimpo);
//							new ContadorSms().gravarPorTipo(tipoInformacao);
							
						}
						
					}
						
				}
				
			}
			
		}
		
	}
	
	public void enviarMensagem(String tel, String msg){
		
		tel = tel.replaceAll("\\D", "");
		
		if(tel.length() == 8){
			
			tel = "5571" + tel;
			
		} else if(tel.length() == 10){
			
			tel = "55" + tel;
			
		} else if(tel.length() != 12){
			
			return;
			
		}
		
		String urlString = "http://sms.televia.com.br/sms/sms.php?tel=param1&msg=param2";
		
		urlString = urlString.replace("param1", tel);
		urlString = urlString.replace("param2", msg);
		urlString = urlString.replaceAll(" ", "+");
		
		System.out.println(urlString);
		
		try {
			
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.connect();
			connection.getContent();
			
//			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//			connection.setRequestMethod("GET");
//			connection.connect();

			
			
			//HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
			
			//connection.setRequestProperty("Request-Method", "GET"); 
			
			//connection.setDoInput(true);  
			//connection.setDoOutput(true);
			
			//connection.connect();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void enviarMensagem2(String celular, String mensagem) {

		StringBuilder url = new StringBuilder("http://sms.televia.com.br/sms/sms.php");

        url.append("?tel=");
		                
        url.append(celular);
		                
        url.append("&msg=");
		                
        mensagem = mensagem.replaceAll(" ","+");
		                
        url.append(mensagem);
		                
        try{
		                        
        	URL conexao = new URL(url.toString());
		                        
        	URLConnection connection = conexao.openConnection();
		                        
        	connection.connect();
		                        
        	connection.getContent();
		                    
        }catch(Exception ex){
		                        
        	
		                    
        }  
	}
	
}
