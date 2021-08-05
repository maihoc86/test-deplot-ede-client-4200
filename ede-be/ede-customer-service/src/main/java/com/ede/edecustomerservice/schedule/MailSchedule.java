package com.ede.edecustomerservice.schedule;

import java.util.LinkedList;
import java.util.Queue;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ede.edecustomerservice.implement.mail.MailEntity;

@Component
public class MailSchedule {

	private Queue<MailEntity> queueList = new LinkedList<>();	
	public void addMail(MailEntity mailEntity) {
		this.queueList.add(mailEntity);
	}
	
	@Autowired
	private JavaMailSender sender;
	
	@Scheduled(fixedDelay = 1000)
	private void run() {
		System.err.println("OK send mail" + this.queueList.size());
		try {
			if (!this.queueList.isEmpty()) {
				this.send(this.queueList.poll());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void send(MailEntity mailEntity) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			
			helper.setFrom(
				String.format("%s <%s>",mailEntity.getNameSender(), mailEntity.getMailSender())
			);
			
			helper.setTo(mailEntity.getMailReceiver());
			helper.setSubject(mailEntity.getSubject());
			helper.setText(mailEntity.getText(), true);
			helper.setReplyTo(mailEntity.getReplyTo());
			
			this.sender.send(message);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
