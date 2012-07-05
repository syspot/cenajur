package br.com.cenajur.faces;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.model.Usuario;
import br.com.topsys.exception.TSApplicationException;



@ManagedBean
@RequestScoped
public class UsuarioFaces implements Serializable  {
	
     
	private Usuario usuario;
	private List<Usuario> usuarios;
   
    public UsuarioFaces() {
       this.usuario = new Usuario();
       this.usuarios = Collections.emptyList();
       
    }

	
	public void salvar() {
		try {
			
			
			usuario.save();
			
			this.usuarios = this.usuario.findAll();
			
						
		} catch (TSApplicationException e) {
			
			e.printStackTrace();
		}
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
