package	texto;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Runnable {

    @Override
    public void run() {
        try {
            ActiveMQConnectionFactory factory = 
            new ActiveMQConnectionFactory("tcp://localhost:61616");
            factory.setTrustAllPackages(true);
           
            //Cria a conexão com ActiveMQ
            Connection connection = factory.createConnection();
            // Inicia a conexão
            connection.start();
         // Cria a sessão
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Crea a fila e informa qual o destinatário.
            Destination queue = session.createQueue("Unipe");            
            MessageConsumer consumer = session.createConsumer(queue);
            Message message = consumer.receive();

        	   	if (message instanceof TextMessage){
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Mensagem: " + text);
            }
        	   	
        	   	if (message instanceof ObjectMessage){
        	   		ObjectMessage objectMessage = (ObjectMessage) message;
                    Paciente paciente = (Paciente) objectMessage.getObject();
                    System.out.println("Objeto Consumido ID: " + paciente.getId());
                    System.out.println("Objeto Consumido Nome: " + paciente.getNome());
                    System.out.println("Objeto Consumido Telefone: " + paciente.getTel());

                }

            session.close();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println("Exception Occured");
        }
    }
}

