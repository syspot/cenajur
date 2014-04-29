package br.com.cenajur.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.cenajur.model.Cidade;
import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Estado;
import br.com.cenajur.model.Lotacao;
import br.com.cenajur.util.EmailUtil;
import br.com.cenajur.util.SMSUtil;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "enviarSMSFaces")
public class EnviarSMSFaces extends TSMainFaces {

	private Cliente clientePesquisa;

	private List<Cliente> grid;

	private List<SelectItem> estados;
	private List<SelectItem> cidades;

	private String msg;
	private String assuntoEmail;
	private String msgEmail;
	private int sleep;

	private int statusCliente;

	@PostConstruct
	protected void init() {

		this.clientePesquisa = new Cliente();
		this.clientePesquisa.setCidade(new Cidade());
		this.clientePesquisa.getCidade().setEstado(new Estado());
		this.clientePesquisa.setLotacao(new Lotacao());

		this.estados = super.initCombo(new Estado().findAll("descricao"), "id", "descricao");

		this.sleep = 5000;
	}

	@Override
	protected String find() {

		this.tratarSituacao();

		this.grid = this.clientePesquisa.findByModel();

		TSFacesUtil.gerarResultadoLista(this.grid);

		return null;

	}

	public String atualizarComboCidadesPesquisa() {
		this.cidades = super.initCombo(this.clientePesquisa.getCidade().findCombo(), "id", "descricao");
		return null;
	}

	private void tratarSituacao() {

		switch (statusCliente) {
		case 1:
			this.clientePesquisa.setFlagAtivo(Boolean.TRUE);
			break;
		case 2:
			this.clientePesquisa.setFlagAtivo(Boolean.FALSE);
			break;
		default:
			this.clientePesquisa.setFlagAtivo(null);
			break;
		}

	}

	public String selecionarTodos() {

		for (Cliente cliente : this.grid) {
			cliente.setFlagSelecionado(true);
		}

		return null;
	}

	public String enviarEmail() {

		if (TSUtil.isEmpty(this.assuntoEmail)) {
			super.addErrorMessage("Assunto: Campo obrigatório");
			return null;
		}

		if (TSUtil.isEmpty(this.msgEmail)) {
			super.addErrorMessage("Mensagem: Campo obrigatório");
			return null;
		}

		if (TSUtil.isEmpty(this.grid)) {
			super.addErrorMessage("Pesquise e selecione o associado");
			return null;
		}

		EmailUtil emailUtil = new EmailUtil();

		boolean achouCliente = false;

		for (Cliente cliente : this.grid) {

			if (cliente.isFlagSelecionado() && !TSUtil.isEmpty(cliente.getEmail())) {

				achouCliente = true;

				this.msgEmail = this.msgEmail.replace("(associado)", cliente.getNome());

				emailUtil.enviarEmailTratado(cliente.getEmail(), this.assuntoEmail, this.msgEmail, "text/html");

				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

		if (achouCliente) {
			super.addInfoMessage("Operação realizada com sucesso");
		} else {
			super.addErrorMessage("Pesquise e selecione o associado");
		}

		return null;

	}

	public String enviarSMS() {

		if (TSUtil.isEmpty(this.msg)) {
			super.addErrorMessage("Mensagem: Campo obrigatório");
			return null;
		}

		if (TSUtil.isEmpty(this.grid)) {
			super.addErrorMessage("Pesquise e selecione o associado");
			return null;
		}

		SMSUtil smsUtil = new SMSUtil();
		
		boolean achouCliente = false;

		for (Cliente cliente : this.grid) {

			if (cliente.isFlagSelecionado() && !TSUtil.isEmpty(cliente.getCelular())) {

				achouCliente = true;
				
				this.msg = this.msg.replace("(associado)", cliente.getNome());

				smsUtil.enviarMensagem(cliente.getCelular(), this.msg);

				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
		
		if (achouCliente) {
			super.addInfoMessage("Operação realizada com sucesso");
		} else {
			super.addErrorMessage("Pesquise e selecione o associado");
		}

		return null;

	}

	public Cliente getClientePesquisa() {
		return clientePesquisa;
	}

	public void setClientePesquisa(Cliente clientePesquisa) {
		this.clientePesquisa = clientePesquisa;
	}

	public List<Cliente> getGrid() {
		return grid;
	}

	public void setGrid(List<Cliente> grid) {
		this.grid = grid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<SelectItem> getEstados() {
		return estados;
	}

	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public int getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(int statusCliente) {
		this.statusCliente = statusCliente;
	}

	public String getMsgEmail() {
		return msgEmail;
	}

	public void setMsgEmail(String msgEmail) {
		this.msgEmail = msgEmail;
	}

	public String getAssuntoEmail() {
		return assuntoEmail;
	}

	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}

}
