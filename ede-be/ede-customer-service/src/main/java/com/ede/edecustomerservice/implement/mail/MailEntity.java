package com.ede.edecustomerservice.implement.mail;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Content of email
 * @author vinh
 */
@NoArgsConstructor
@AllArgsConstructor
public class MailEntity {
	
	@Getter // FIXME sửa nội dung người gửi thành mail dùng chung của team
	private String replyTo = "vinhlmpc01238@fpt.edu.vn";
	@Getter
	private String nameSender = "EC Dark Eye";
	@Getter // FIXME sửa nội dung người gửi thành mail dùng chung của team
	private String mailSender = "noreply.vinh.bot@gmail.com";
	
	@Setter
	@Getter
	private String mailReceiver;
	@Setter
	@Getter
	private String subject;
	@Setter
	@Getter
	private File attachment;
	/**
	 * Format text is html, this is content of mail
	 */
	@Setter
	@Getter
	private String text;
}
