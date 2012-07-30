package br.com.cenajur.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.cenajur.model.Mensagem;

@SessionScoped
@ManagedBean(name = "mensagemFaces")
public class MensagemFaces extends CrudFaces<Mensagem> {

	private List<Mensagem> mensagens;
	
	private Mensagem mensagemSelecionada;
	
	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	protected void clearFields() {
		this.mensagens = new ArrayList<Mensagem>();
		Mensagem mensagem = null;
		
		for (int i = 0; i < 10; i++) {
			
			mensagem = new Mensagem("Colaborador " + i, "Por favor, entregue a documentação ao Advogado Drº. Boris... ", i);
			this.mensagens.add(mensagem);
			
		}
	}
	
	public List<Mensagem> getMensagens() {
		return mensagens;
	}

	public void setMensagens(List<Mensagem> mensagens) {
		this.mensagens = mensagens;
	}

	public Mensagem getMensagemSelecionada() {
		return mensagemSelecionada;
	}

	public void setMensagemSelecionada(Mensagem mensagemSelecionada) {
		this.mensagemSelecionada = mensagemSelecionada;
	}
	
}
