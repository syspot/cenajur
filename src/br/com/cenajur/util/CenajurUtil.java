package br.com.cenajur.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.topsys.util.TSUtil;

public class CenajurUtil {

	private CenajurUtil(){
		
	}
	
	public static String[] getVetor(String... parameters){
		
		String[] vetor = null;
		
		if(!TSUtil.isEmpty(parameters)){
			
			vetor = new String[parameters.length];
			int i = 0;
			
			for(String str : parameters){
				
				vetor[i] = str;
				i++;
				
			}
			
		}
		
		return vetor;
		
	}
	
	public static void addErrorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", msg));
	}
	
	public static void addInfoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", msg));
	}
	
	public static String tratarString(String str){
		return (str == null) ? str : "%" + str.toLowerCase() + "%"; 
	}
}
