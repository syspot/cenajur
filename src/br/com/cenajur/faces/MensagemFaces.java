package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Mensagem;
import br.com.cenajur.model.MensagemDestinatario;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "mensagemFaces")
public class MensagemFaces{

	private Mensagem mensagem;
	
	private MensagemDestinatario mensagemDestinatarioSelecionada;
	
	private List<MensagemDestinatario> mensagensDestinatarios;
	
	private Colaborador destinatarioSelecionado;
	
	private Colaborador colaboradorConectado;
	
	@PostConstruct
	protected void init() {
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
		this.clearFields();
	}
	
	protected void clearFields() {
		this.mensagem = new Mensagem();
		this.mensagem.setMensagensDestinatarios(new ArrayList<MensagemDestinatario>());
		this.pesquisarMensagens();
	}
	
	public void pesquisarMensagens(){
		this.mensagensDestinatarios = new MensagemDestinatario().pesquisarPorColaborador(colaboradorConectado);
	}
	
	public String enviarMensagem() throws TSApplicationException{
		
		RequestContext context = RequestContext.getCurrentInstance();
		
		if(TSUtil.isEmpty(this.mensagem.getMensagensDestinatarios())){
			context.addCallbackParam("sucesso", false);
			CenajurUtil.addErrorMessage("Destinatário: Campo obrigatório");
			return null;
		}
		
		context.addCallbackParam("sucesso", true);
		
		this.mensagem.setRemetente(ColaboradorUtil.obterColaboradorConectado());
		this.mensagem.setData(new Date());
		this.mensagem.save();
		this.clearFields();
		return null;
	}
	
	public String lerMensagem() throws TSApplicationException{
		this.mensagemDestinatarioSelecionada.setFlagLida(Boolean.TRUE);
		this.mensagemDestinatarioSelecionada.update();
		return null;
		
	}
	
	public String addDestinatario(){
		MensagemDestinatario mensagemDestinatario = new MensagemDestinatario();
		mensagemDestinatario.setMensagem(this.mensagem);
		mensagemDestinatario.setFlagLida(Boolean.FALSE);
		mensagemDestinatario.setDestinatario(this.destinatarioSelecionado);
		this.mensagem.getMensagensDestinatarios().add(mensagemDestinatario);
		return null;
	}

	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

	public MensagemDestinatario getMensagemDestinatarioSelecionada() {
		return mensagemDestinatarioSelecionada;
	}

	public void setMensagemDestinatarioSelecionada(MensagemDestinatario mensagemDestinatarioSelecionada) {
		this.mensagemDestinatarioSelecionada = mensagemDestinatarioSelecionada;
	}

	public List<MensagemDestinatario> getMensagensDestinatarios() {
		return mensagensDestinatarios;
	}

	public void setMensagensDestinatarios(List<MensagemDestinatario> mensagensDestinatarios) {
		this.mensagensDestinatarios = mensagensDestinatarios;
	}

	public Colaborador getDestinatarioSelecionado() {
		return destinatarioSelecionado;
	}

	public void setDestinatarioSelecionado(Colaborador destinatarioSelecionado) {
		this.destinatarioSelecionado = destinatarioSelecionado;
	}
	
}
