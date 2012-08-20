package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Mensagem;
import br.com.cenajur.model.MensagemDestinatario;
import br.com.cenajur.util.ColaboradorUtil;
import br.com.topsys.exception.TSApplicationException;

@ViewScoped
@ManagedBean(name = "mensagemFaces")
public class MensagemFaces{

	private Mensagem mensagem;
	
	private Mensagem mensagemSelecionada;
	
	private List<Mensagem> mensagens;
	
	private Colaborador destinatarioSelecionado;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	protected void clearFields() {
		this.mensagem = new Mensagem();
		this.mensagem.setMensagensDestinatarios(new ArrayList<MensagemDestinatario>());
		this.mensagens = new Mensagem().pesquisarPorColaborador(ColaboradorUtil.obterColaboradorConectado());
	}
	
	public String enviarMensagem() throws TSApplicationException{
		this.mensagem.setRemetente(ColaboradorUtil.obterColaboradorConectado());
		this.mensagem.save();
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

	public Mensagem getMensagemSelecionada() {
		return mensagemSelecionada;
	}

	public void setMensagemSelecionada(Mensagem mensagemSelecionada) {
		this.mensagemSelecionada = mensagemSelecionada;
	}

	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public Colaborador getDestinatarioSelecionado() {
		return destinatarioSelecionado;
	}

	public void setDestinatarioSelecionado(Colaborador destinatarioSelecionado) {
		this.destinatarioSelecionado = destinatarioSelecionado;
	}
	
}
