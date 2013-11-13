package br.com.cenajur.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.cenajur.model.Email;
import br.com.topsys.util.TSUtil;

public class EmailUtil {
	
    private static final String mailSMTPServer = "smtp.gmail.com";
    private static final String mailSMTPServerPort = "465";
    
    public void enviarEmailTratado(String to, String subject, String message, String mimeType){
    	
    	try {
			this.sendMail(to, subject, message, mimeType);
		} catch (Exception e) {
			CenajurUtil.addErrorMessage("Ocorreu um erro ao enviar o e-mail");
			e.printStackTrace();
		}
    	
    }
	
    public void sendMail(String to, String subject, String message, String mimeType) throws AddressException, MessagingException {  
          
        Properties props = new Properties();
        
        Email email = new Email(Constantes.EMAIL_ID);
        
        email = email.getById();
  
        props.put("mail.transport.protocol", "smtp");   
        props.put("mail.smtp.starttls.enable","true");   
        props.put("mail.smtp.host", mailSMTPServer);   
        props.put("mail.smtp.auth", "true");   
        props.put("mail.smtp.user", email.getEmail());  
        props.put("mail.debug", "false");  
        props.put("mail.smtp.port", mailSMTPServerPort);
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort);  
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");  
          
        SimpleAuth auth = null;  
        auth = new SimpleAuth (email.getEmail(), email.getSenha());  
          
        //Session - objeto que ira realizar a conexão com o servidor  
        /*Como há necessidade de autenticação é criada uma autenticacao que 
         * é responsavel por solicitar e retornar o usuário e senha para  
         * autenticação */  
        Session session = Session.getDefaultInstance(props, auth);  
        //session.setDebug(true); //Habilita o LOG das ações executadas durante o envio do email  
  
        //Objeto que contém a mensagem  
        Message msg = new MimeMessage(session);  

        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
        msg.setFrom(new InternetAddress(email.getEmail()));  
        msg.setSubject(subject);  

        if(TSUtil.isEmpty(mimeType)){
        	msg.setContent(message, "text/plain");  
        } else{
        	msg.setContent(message, mimeType);
        }
          
        Transport tr;  

        tr = session.getTransport("smtp");
        tr.connect(mailSMTPServer, email.getEmail(), email.getSenha());  
        msg.saveChanges();
        tr.sendMessage(msg, msg.getAllRecipients());  
        tr.close();  
  
    }  
}  
  
	//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp  
	class SimpleAuth extends Authenticator {  
	
		public String username = null;  
		public String password = null;  
  
		public SimpleAuth(String user, String pwd) {  
			username = user;  
			password = pwd;  
		}  
  
		protected PasswordAuthentication getPasswordAuthentication() {  
			return new PasswordAuthentication (username, password);  
    	}  
	}
