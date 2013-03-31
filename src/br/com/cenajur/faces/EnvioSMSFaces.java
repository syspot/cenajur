package br.com.cenajur.faces;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;
import br.com.topsys.util.TSUtil;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name = "envioSMSFaces")
public class EnvioSMSFaces implements Serializable {

	private Cliente clienteSelecionado;
	private String msg;
	
	@PostConstruct
	protected void init() {
		this.clienteSelecionado = new Cliente();
	}
	
	public String enviarMsg(){
		
		if(TSUtil.isEmpty(clienteSelecionado) || TSUtil.isEmpty(clienteSelecionado.getId())){

			List<Cliente> clientes = new Cliente().pesquisarClientesComCelular();
			
			for(Cliente cliente : clientes){
				
				//this.enviarSMS(cliente.getCelular(), msg);
				
			}
			
		} else{
			
			this.clienteSelecionado = this.clienteSelecionado.getById();
			
			//this.enviarSMS(this.clienteSelecionado.getCelular(), msg);
			
		}
		
		return null;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}
	
}
