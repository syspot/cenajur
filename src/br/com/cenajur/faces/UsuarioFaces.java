package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;

import br.com.cenajur.model.Usuario;
import br.com.topsys.exception.TSApplicationException;



@ManagedBean()
public class UsuarioFaces  {
	
     
	private Usuario usuario;
   
    public UsuarioFaces() {
       this.usuario = new Usuario();
       
    }

	
	public void salvar() {
		try {
			
			
			usuario.save();
			
						
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


	

}
