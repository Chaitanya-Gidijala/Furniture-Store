package com.foryou.webapp.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendEmail(String recipient, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(content);

		mailSender.send(message);
	}

//	public void sendEmailWithAttachment(String recipient, String subject, String content, String attachment)
//			throws MessagingException {
//
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(recipient);
//		message.setSubject(subject);
//		message.setText(content);
//		
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//		FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
//
//		mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//
//		mailSender.send(mimeMessage);
//		System.out.println("Mail send successfully....");
//	}

//	public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
//		MimeMessage message = mailSender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			helper.setTo(to);
//			helper.setSubject(subject);
//			helper.setText(text);
//
//			// Attach the file
//			File attachment = new File(attachmentPath);
//			helper.addAttachment(attachment.getName(), attachment);
//
//			// Send the email
//			mailSender.send(message);
//		} catch (MessagingException e) {
//			// Handle exception
//			e.printStackTrace();
//		}

	

}
