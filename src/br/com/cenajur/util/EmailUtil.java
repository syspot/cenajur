package br.com.cenajur.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.cenajur.model.Email;

public class EmailUtil {

	private static final String mailSMTPServer = "smtp.gmail.com";
	private static final String mailSMTPServerPort = "465";

	public void enviarEmailTratado(String to, String subject, String message, String mimeType) {

		try {

			Properties props = new Properties();

			props.put("mail.debug", "true");
			props.put("mail.host", mailSMTPServer);
			props.put("mail.smtp.port", mailSMTPServerPort);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true");

			Email email = new Email(Constantes.EMAIL_ID);

			email = email.getById();

			Session session = Session.getInstance(props, new SimpleAuth(email.getEmail(), email.getSenha()));

			MimeMessage msg = new MimeMessage(session);

			msg.setText(message);
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