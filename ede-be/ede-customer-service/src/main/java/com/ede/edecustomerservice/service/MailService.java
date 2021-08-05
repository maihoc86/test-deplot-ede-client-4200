package com.ede.edecustomerservice.service;

import org.springframework.stereotype.Service;

import com.ede.edecustomerservice.implement.mail.MailEntity;

/**
 * Service use to send mail (width email is child of {@link MailService}
 * @author vinh
 */
@Service
public interface MailService {
	
	public void addMail(MailEntity mail);

}
