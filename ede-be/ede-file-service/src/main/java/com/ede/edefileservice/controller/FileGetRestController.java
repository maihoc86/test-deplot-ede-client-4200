package com.ede.edefileservice.controller;

import java.io.IOException;
import java.io.InputStream;


import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edefileservice.service.FTPClientManager;

/**
 * Lớp dùng để lấy file từ FTP Server
 * @author Vinh
 *
 */
@RestController
@RequestMapping("/ede-file")
public class FileGetRestController {
	
	@Autowired
	FTPClientManager ftpManager;
	
	@GetMapping("gets")
	ResponseEntity<String[]> getListNames() {
		return ResponseEntity.ok(this.ftpManager.getListNames());
	}
	
	@GetMapping(value = "get/image/{filename}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
	ResponseEntity<byte[]> getFile(@PathVariable("filename") String fileName) throws IOException {
		return this.getFile(fileName, FTP.BINARY_FILE_TYPE);
	}
	
	private ResponseEntity<byte[]> getFile(String fileName, int FTP_FILE_TYPE) throws IOException {
		InputStream is = this.ftpManager.getFile(fileName,  FTP_FILE_TYPE);
		if (null == is) {
			// XXX Hot Fix
			return ResponseEntity.ok(this.ftpManager.getFile("no-document.png",  FTP_FILE_TYPE).readAllBytes());
		}
		return ResponseEntity.ok(is.readAllBytes());
	}
	
}
