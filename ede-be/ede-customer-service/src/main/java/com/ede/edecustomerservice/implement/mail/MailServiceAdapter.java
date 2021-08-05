package com.ede.edecustomerservice.implement.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.schedule.MailSchedule;
import com.ede.edecustomerservice.service.MailService;

@Service
public class MailServiceAdapter implements MailService {
	
	@Autowired
	MailSchedule mailSchedule;

	@Override
	public void addMail(MailEntity mail) {
		this.mailSchedule.addMail(mail);
	}
	
	

}
