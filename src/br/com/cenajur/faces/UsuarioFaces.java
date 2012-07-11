package br.com.cenajur.faces;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Usuario;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.web.faces.TSMainFaces;

@ManagedBean
@RequestScoped
public class UsuarioFaces extends TSMainFaces {

	private Usuario usuario;
	private List<Usuario> usuarios;
	
	


	public UsuarioFaces() {
		this.usuarios = Collections.emptyList();
	}

	@Override
	protected String insert() throws TSApplicationException {

		usuario.save();

		this.usuarios = this.usuario.findAll();
		
		return null;
	}
	
	public void pesquisar() {
		this.usuarios = this.usuario.findByModel();
	}
	
	
	@Override
	@PostConstruct
	protected void clearFields() {
		this.usuario = new Usuario();
		
	}

	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}
