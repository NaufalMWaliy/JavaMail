/**
 * Java Mail Application, By :
 * 	
 * 	Mughie Arif
 * 	Muhammad Fadhil
 * 	Naufal Muntaaza
 * 
 * Copyright 2018
 */

package application;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.mail.*;
import javax.mail.internet.*;

public class JavaMail implements Runnable {
	static String email, password;
	static Properties props;
	static Session session;
	/*
	 * CHANGE MAXTHREAD IF TO MUCH.
	 */
	static final int MAXTHREAD = 1000;
	static int sendedMail = 0;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Email : "); email = scanner.nextLine();
		System.out.print("Password : "); password = scanner.nextLine();
		
		// Set SMTP Properties
		setProperties();
		// Login to Your GMAIL
		Login();
		
		// Start Time
		long startTime = System.currentTimeMillis();
		
		Runnable runnable = new JavaMail();
		// Thread will be create
		Thread[] threads = new Thread[MAXTHREAD];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(runnable);
			threads[i].start();
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// End Time
		long endTime = System.currentTimeMillis();
		// Caluculate Total Execution Time
		System.out.println(String.format("Sent %d Mail From %d", sendedMail, MAXTHREAD));
		System.out.println(String.format("Total Execution Time : %d Second or %d Millisecond ", TimeUnit.MILLISECONDS.toSeconds(endTime - startTime), (endTime - startTime)));
	}
	
	public void run() {
		
		SendMail();
    }
	
	public static void setProperties() {
		
		// Properties
		props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.debug", "true");
		props.put("mail.smtp.starttsl.enable", "true");
	}
	
	public static void Login() {
		
		// Authentication
		session = Session.getInstance(props, 
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(email, password);
					}
				});
	}
	
	public  static void SendMail() {
		
        try {
        	MimeMessage msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(email));
	        msg.setRecipients(Message.RecipientType.TO, "naufal.m.w.h@gmail.com");
	        msg.setSubject("Simple Test Java Mail");
	        msg.setSentDate(new Date());
	        Multipart multipart = new MimeMultipart();
	        
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        
	        Address[] fromAddress = msg.getFrom();
	        Address[] recipientAddress = msg.getAllRecipients();
	        		
	        String htmlContent = "<html>"
	        		+ "<head><style>"
	        		+ ".button {\r\n" + 
	        		"    background-color: #4CAF50;\r\n" + 
	        		"    border: none;\r\n" + 
	        		"    color: white;\r\n" + 
	        		"    padding: 15px 32px;\r\n" + 
	        		"    text-align: center;\r\n" + 
	        		"    text-decoration: none;\r\n" + 
	        		"    display: inline-block;\r\n" + 
	        		"    font-size: 16px;\r\n" + 
	        		"    margin: 4px 2px;\r\n" + 
	        		"    cursor: pointer;\r\n" + 
	        		"}	"
	        		+ "</style></head>"
	        		+ "<body>"
	        		+ "<p>Hi, " + ((InternetAddress) recipientAddress[0]).getAddress() + "</p>"
	        		+ "<p>Anda mendapatkan undangan dari " + ((InternetAddress) fromAddress[0]).getAddress() + " untuk mendaftar sebagai user di Travlendar oleh PT A2.</p>"
	        		+ "<br />"
	        		+ "<p>Sudah lelah dengan sulitnya travelling? atau bingung mencari kendaraan yang cocok? Yuk Coba Travlendar. "
	        		+ "Travlendar merupakan aplikasi untuk memudahkan seseorang melakukan traveling, "
	        		+ "dari bagaimana mengatur jadwalnya, menentukan lokasinya bahkan note apabila anda merasa sering lupa akan ssuatu hal. "
	        		+ "Semua itu dapat diatasi oleh Travlendar kare Travlendar dibuat untuk meningkatkan produktivitas anda dalam ber-traveling. "
	        		+ "Apalagi yang anda tunggu! Saatnya join menjadi user di Travlendar.</p>"
	        		+ "<br /><br />"
	        		+ "<a href=\"a2.proyek3.jtk.polban.ac.id\" class=\"button\">Travlendar</a>"
	        		+ "</body></html>";
	        htmlPart.setContent(htmlContent, "text/html");
	        multipart.addBodyPart(htmlPart);
	        
	        msg.setContent(multipart);
	        Transport.send(msg);
	        sendedMail++;
			
	        System.out.println("---Done---");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
