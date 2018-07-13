package com.book.services.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;

@Slf4j
@Component
public class MailSenderUtils {
	private final JavaMailSender mailSender;
	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	public MailSenderUtils(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendMail(String to, String subject, String content) {
		MimeMessagePreparator prepareMessage = mimeMessage -> {
			mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setFrom(new InternetAddress(from));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(content, "utf-8", "html");
		};
		try {
			mailSender.send(prepareMessage);
		} catch (MailException e) {
			log.error("MailException", e);
			throw new RuntimeException("Mail send failed. To :" + to);
		}
	}
}
