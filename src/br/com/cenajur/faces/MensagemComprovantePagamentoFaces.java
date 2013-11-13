package br.com.cenajur.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.MensagemComprovantePagamento;
import br.com.cenajur.util.Constantes;

@ViewScoped
@ManagedBean(name = "mensagemComprovantePagamentoFaces")
public class MensagemComprovantePagamentoFaces extends CrudFaces<MensagemComprovantePagamento> {

	@PostConstruct
	protected void init() {
		this.clearFields();
	}
	
	@Override
	public String limpar() {
		setCrudModel(new MensagemComprovantePagamento(Constantes.MENSAGEM_COMPROVANTE_PAGAMENTO_ID).getById());
		setTabIndex(1);
		return null;
	}
	
}
