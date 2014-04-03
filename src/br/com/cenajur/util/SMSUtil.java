package br.com.cenajur.util;

import java.net.URL;
import java.net.URLConnection;

import br.com.cenajur.model.Email;
import br.com.topsys.util.TSStringUtil;
import br.com.topsys.util.TSUtil;

public class SMSUtil {

	public void enviarMensagem(String tel, String msg) {

		Email email = new Email(Constantes.EMAIL_ID);

		email = email.getById();
		
		if(email.getFlagSMSAtivo()){
			
			msg = TSStringUtil.removerAcentos(msg);
			
			String urlString = email.getUrlSMS() + "sendsms.php?message=" + (msg.length() > 160 ? msg.substring(0, 160) : msg).replaceAll(" ", "%20")  + "&telefone=" + TSUtil.removerNaoDigitos(tel);
			
			try {
				
				URL url = new URL(urlString);
				URLConnection connection = url.openConnection();
				connection.connect();
				connection.getContent();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
		
		}		

	}
}