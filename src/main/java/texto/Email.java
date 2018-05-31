package texto;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Email {
	
	public void run(){
            Properties props = new Properties();
            /** Par�metros de conex�o com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
 
            Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                             protected PasswordAuthentication getPasswordAuthentication() 
                             {
                                   return new PasswordAuthentication("testeactivemq@gmail.com", "activemq123");
                             }
                        });
 
            /** Ativa Debug para sess�o */
            session.setDebug(true);
 
            try {
 
                  Message message = new MimeMessage(session);
                  message.setFrom(new InternetAddress("activemq@gmail.com")); //Remetente
 
                  Address[] toUser = InternetAddress //Destinat�rio(s)
                             .parse("flaviorcabral@gmail.com, joaonildocc@gmail.com, luizesantoneto@gmail.com");  
 
                  message.setRecipients(Message.RecipientType.TO, toUser);
                  message.setSubject("Envido Para o Consumo");//Assunto
                  message.setText("Enviei este email utilizando Javax.Mail para informar que existe objeto a ser consumido utilizando o activemq!");
                  /**M�todo para enviar a mensagem criada*/
                  Transport.send(message);
 
             } catch (MessagingException e) {
                  throw new RuntimeException(e);
            }
      }
}