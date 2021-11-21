package com.ede.edecustomerservice.schedule;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
	void run() {
		if (!this.queueList.isEmpty()) {
			this.send(this.queueList.poll());
		}
	}

	void send(MailEntity mailEntity) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

			helper.setFrom(String.format("%s <%s>", mailEntity.getNameSender(), mailEntity.getMailSender()));

			helper.setTo(mailEntity.getMailReceiver());
			helper.setSubject(mailEntity.getSubject());
			helper.setText(mailEntity.getText(), true);
			helper.setReplyTo(mailEntity.getReplyTo());
			this.sender.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String createFileName(MultipartFile partFile) {
		String extend = partFile.getOriginalFilename().substring(partFile.getOriginalFilename().lastIndexOf("."));
		String result = "f_" + Long.toHexString(new Date().getTime()) + "_" + UUID.randomUUID().toString() + extend;
		return result;
	}

}
