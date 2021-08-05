package com.ede.edecustomerservice.implement.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.service.MailService;

@Service
public class MailImpl implements MailService {
	
	private MailEntity mailEntity;

	@Autowired
	private JavaMailSender sender;
	
//	private Queue<MailEntity> queueList = new LinkedList<>();

	@Override
	public void send() {
		try {
//			MailEntity mailEntity = this.queueList.poll();
			
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			
			helper.setFrom(
				String.format("%s <%s>",this.mailEntity.getNameSender(), this.mailEntity.getMailSender())
			);
			
			helper.setTo(this.mailEntity.getMailReceiver());
			helper.setSubject(mailEntity.getSubject());
			helper.setText(mailEntity.getText(), true);
			helper.setReplyTo(mailEntity.getReplyTo());
			
			this.sender.send(message);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	@Scheduled(fixedDelay = 5000)
	private void run() {
		try {
			if (!this.queueList.isEmpty()) {
				this.send(queueList.poll());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	@Override
	public void addMail(MailEntity mailEntity) {
//		this.queueList.add(mailEntity);
		this.mailEntity = mailEntity;
	}

	@Override
	public MailEntity getMail() {
		return this.mailEntity;
	}

}
