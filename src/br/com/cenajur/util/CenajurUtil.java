package br.com.cenajur.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.UploadedFile;

import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
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
	
	public static void criaArquivo(UploadedFile file, String arquivo) {
		
		try {
			FileUtils.copyInputStreamToFile(file.getInputstream(), new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
		
	}
	
	public static void criaArquivo(byte[] bytes, String arquivo) {
		
		try {
			FileUtils.copyInputStreamToFile(new ByteArrayInputStream(bytes), new File(arquivo));
		} catch (Exception ex) {
			throw new TSSystemException(ex);
		}
		
	}
	
	public static String obterNomeArquivo(UploadedFile file) {
		
		if(TSUtil.isEmpty(file)){
			return null;
		}
			
		if(file.getFileName().contains("\\")){
			
			String[] fileName = file.getFileName().split("\\\\");
			
			return fileName[fileName.length-1];
			
		} else{
			
			return file.getFileName();
			
		}
	}
	
	public static String getAnoMes(Date data) {
		return TSUtil.isEmpty(data) ? null : TSParseUtil.dateToString(data, TSDateUtil.YYYY) + File.separator + TSParseUtil.dateToString(data, TSDateUtil.MM) + File.separator;
	}

	public static String getAnoMesWeb(Date data) {
		return TSUtil.isEmpty(data) ? null : TSParseUtil.dateToString(data, TSDateUtil.YYYY) + "/" + TSParseUtil.dateToString(data, TSDateUtil.MM) + "/";
	}
	
	public static final List<SelectItem> initCombo(Collection coll,String nomeValue,String nomeLabel) {
		List<SelectItem> list=new ArrayList<SelectItem>();
		for(Object o:coll){
			try {
				list.add(new SelectItem(BeanUtils.getProperty(o,nomeValue),BeanUtils.getProperty(o,nomeLabel)));
			} catch (Exception e) {
				e.printStackTrace();
				throw new TSSystemException(e);
			} 
		}
		return list;
	}
	
	public static long gerarNumeroAleatorio() {
		return (long) ((10000 * Math.random()) * (100 * Math.random()));
	}
}
