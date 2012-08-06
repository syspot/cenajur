package br.com.cenajur.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
	
	public static void addDangerMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ATEN«√O", msg));
	}
	
	public static void addErrorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", msg));
	}
	
	public static void addInfoMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", msg));
	}
	
	public static String tratarString(String str){
		return "%" + str.toLowerCase() + "%"; 
	}
	
	public static String semAcento(String campo){
		return "translate(lower(trim(".concat(campo).concat(")), '·ÈÌÛ˙‡ËÏÚ˘„ı‚ÍÓÙÙ‰ÎÔˆ¸Á¡…Õ”⁄¿»Ã“Ÿ√’¬ Œ‘€ƒÀœ÷‹«', 'aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC')");
	}
	
	public static Date getTrimestreAnterior(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, c.get(Calendar.MONTH)-3);
		return c.getTime();
	}
	
	public static void criaArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
