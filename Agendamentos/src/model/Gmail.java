package model;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Gmail {

	public static boolean EnviaEmail(String emailTo, String mensagem, String assunto) {
		boolean enviou = false;
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
//			email.setHostName("smtp.mandrillapp.com");
//			email.setHostName("smtp.poa.terra.com.br");
//			email.setHostName("smtp.live.com");
			email.setSmtpPort(25);
			email.setAuthentication("agenda.brancobarbershop@gmail.com", "agenda1234");
			email.setSSLOnConnect(true);

			email.setFrom("agenda.brancobarbershop@gmail.com");
			email.setSubject(assunto);
			email.setMsg(mensagem);
			email.addTo(emailTo);
			email.send();
			System.out.println("se pa funcionou");
			enviou = true;
		} catch (EmailException e) {
			e.printStackTrace();
		}
		return enviou;
	}
}
