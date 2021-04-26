package com.sudokutorial.service;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sudokutorial.model.event.OnRegistrationCompleteEvent;

@Service
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.enable}")
	private Boolean enable;
    
	private void send(MimeMessagePreparator preparator) {
		if (enable) {
			mailSender.send(preparator);
		}
	}

	@Async
	public void sendVerificationEmail(OnRegistrationCompleteEvent event) {
		Context context = new Context();
		context.setVariable("token", event.getToken());
		context.setVariable("url", event.getUrl());

		String emailContent = templateEngine.process("mail/verifyemail.html", context);

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);

				message.setTo(event.getEmail());
				message.setFrom(new InternetAddress("no-reply@sudokutorial.com"));
				message.setSubject("Verify Your Email Address");
				message.setSentDate(new Date());

				message.setText(emailContent, true);
			}

		};

		send(preparator);
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.sendVerificationEmail(event);
	}
	
	

}
