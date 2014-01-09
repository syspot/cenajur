package br.com.cenajur.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.cenajur.model.Email;

public class EmailUtil {

	public void enviarEmailTratado(String to, String subject, String message, String mimeType) {

		try {

			Properties props = new Properties();

			Email email = new Email(Constantes.EMAIL_ID);

			email = email.getById();

			props.put("mail.debug", "false");
			props.put("mail.smtp.host", email.getSmtp());
			props.put("mail.smtp.port", email.getPorta().toString());
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "false");

			Session session = Session.getInstance(props, new SimpleAuth(email.getEmail(), email.getSenha()));

			MimeMessage msg = new MimeMessage(session);
			Multipart multiPart = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(message, "text/html; charset=utf-8");

			multiPart.addBodyPart(htmlPart);

			msg.setContent(multiPart);
			msg.setSubject(subject);
			msg.setFrom(new InternetAddress(email.getEmail()));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			Transport.send(msg);

		} catch (Exception e) {

			CenajurUtil.addErrorMessage("Ocorreu um erro ao enviar o e-mail");

			e.printStackTrace();

		}

	}

	class SimpleAuth extends Authenticator {

		public String username = null;
		public String password = null;

		public SimpleAuth(String user, String pwd) {

			username = user;
			password = pwd;

		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}

	}

}