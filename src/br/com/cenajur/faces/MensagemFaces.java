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
	
	private List<MensagemDestinatario> mensagensDestinatariosRecebidas;
	private List<Mensagem> mensagensEnviadas;
	
	private Colaborador destinatarioSelecionado;
	
	private Colaborador colaboradorConectado;
	
	private int qtdMensagensNaoLidas;
	
	private boolean flagEnviar;
	
	private Integer tabIndex;
	
	private Mensagem mensagemBusca;
	private MensagemDestinatario mensagemDestinatarioBusca;
	private int statusBuscaLida;
	
	@PostConstruct
	protected void init() {
		this.mensagemBusca = new Mensagem();
		this.mensagemDestinatarioBusca = new MensagemDestinatario();
		this.colaboradorConectado = ColaboradorUtil.obterColaboradorConectado();
		this.clearFields();
	}
	
	public String limpar(){
		this.mensagem = new Mensagem();
		this.mensagem.setMensagensDestinatarios(new ArrayList<MensagemDestinatario>());
		return null;
	}
	
	protected void clearFields() {
		this.limpar();
		this.pesquisarMensagens();
		this.qtdMensagensNaoLidas = new MensagemDestinatario().obterQtdNaoLidas(colaboradorConectado).intValue();
	}
	
	private void tratarStatusLida(){
		
		switch(statusBuscaLida){
			case 1: this.mensagemDestinatarioBusca.setFlagLida(Boolean.TRUE); break;
			case 2: this.mensagemDestinatarioBusca.setFlagLida(Boolean.FALSE); break;
			default: this.mensagemDestinatarioBusca.setFlagLida(null); break;
		}
		
	}
	
	public String pesquisarMensagens(){
		tratarStatusLida();
		this.mensagensDestinatariosRecebidas = this.mensagemDestinatarioBusca.pesquisarPorColaborador(colaboradorConectado);
		this.mensagensEnviadas = this.mensagemBusca.pesquisarPorColaborador(this.colaboradorConectado);
		setTabIndex(1);
		return null;
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
		this.mensagem.setFlagAtivo(Boolean.TRUE);
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
		mensagemDestinatario.setFlagAtivo(Boolean.TRUE);
		if(this.mensagem.getMensagensDestinatarios().contains(mensagemDestinatario)){
			CenajurUtil.addErrorMessage("Esse destinatário já foi adicionado");
		} else{
			this.mensagem.getMensagensDestinatarios().add(mensagemDestinatario);
			CenajurUtil.addInfoMessage("Destinatário adicionado com sucesso");
		}
		return null;
	}
	
	public String excluirMensagemDestinatario() throws TSApplicationException{
		this.mensagemDestinatarioSelecionada.setFlagAtivo(Boolean.FALSE);
		this.mensagemDestinatarioSelecionada.update();
		this.clearFields();
		return null;
	}
	
	public String excluirMensagem() throws TSApplicationException{
		this.mensagem.setFlagAtivo(Boolean.FALSE);
		this.mensagem.update();
		this.clearFields();
		return null;
	}
	
	public String getTitleAbaMensagem(){
		return this.qtdMensagensNaoLidas > 1 ? this.qtdMensagensNaoLidas + " mensagens não lidas" : this.qtdMensagensNaoLidas + " mensagem não lida";
	}
	
	public String getCssTitleAbaMensagem(){
		return this.qtdMensagensNaoLidas > 0 ? "mensagemNaoLida" : "";
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

	public List<MensagemDestinatario> getMensagensDestinatariosRecebidas() {
		return mensagensDestinatariosRecebidas;
	}

	public void setMensagensDestinatariosRecebidas(List<MensagemDestinatario> mensagensDestinatariosRecebidas) {
		this.mensagensDestinatariosRecebidas = mensagensDestinatariosRecebidas;
	}

	public List<Mensagem> getMensagensEnviadas() {
		return mensagensEnviadas;
	}

	public void setMensagensEnviadas(List<Mensagem> mensagensEnviadas) {
		this.mensagensEnviadas = mensagensEnviadas;
	}

	public Colaborador getDestinatarioSelecionado() {
		return destinatarioSelecionado;
	}

	public void setDestinatarioSelecionado(Colaborador destinatarioSelecionado) {
		this.destinatarioSelecionado = destinatarioSelecionado;
	}

	public int getQtdMensagensNaoLidas() {
		return qtdMensagensNaoLidas;
	}

	public void setQtdMensagensNaoLidas(int qtdMensagensNaoLidas) {
		this.qtdMensagensNaoLidas = qtdMensagensNaoLidas;
	}

	public boolean isFlagEnviar() {
		return flagEnviar;
	}

	public void setFlagEnviar(boolean flagEnviar) {
		this.flagEnviar = flagEnviar;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public MensagemDestinatario getMensagemDestinatarioBusca() {
		return mensagemDestinatarioBusca;
	}

	public void setMensagemDestinatarioBusca(MensagemDestinatario mensagemDestinatarioBusca) {
		this.mensagemDestinatarioBusca = mensagemDestinatarioBusca;
	}

	public int getStatusBuscaLida() {
		return statusBuscaLida;
	}

	public void setStatusBuscaLida(int statusBuscaLida) {
		this.statusBuscaLida = statusBuscaLida;
	}
	
}
