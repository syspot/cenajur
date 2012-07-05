package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;
import javax.persistence.MappedSuperclass;

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


	

}
