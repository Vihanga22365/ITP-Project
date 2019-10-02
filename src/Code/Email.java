/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Vihanga
 */
public class Email {
    
    
    private String user = "vihangamihirangaz1@gmail.com";
    private String pass = "dvp22365";
    
    public Email(String to , String subject , String msg)
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()
            {
                return new javax.mail.PasswordAuthentication(user, pass);
            }
            
        });
        
        try
        {
            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("no-reply@gmail.com"));  
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(msg);
            
            Transport.send(message);
            
            System.out.println("Mail Sent Successfully");
            JOptionPane.showMessageDialog(null,"Email Send Successfull !");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
