package com.ede.edefileservice.controller;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ede.edefileservice.service.FTPClientManager;

/**
 * Lớp dùng để upload dữ liệu lên FTP SERVER
 * @author Vinh
 *
 */
@RestController
@RequestMapping("/ede-file")
public class FileCreateRestController {

	@Autowired
	FTPClientManager myFtp;
	
	@PostMapping("create/binary")
	ResponseEntity<String> putFile(@RequestPart("file") MultipartFile partFile) throws IOException{
		return addFile(partFile, FTP.BINARY_FILE_TYPE);
	}
	
	private ResponseEntity<String> addFile(MultipartFile partFile, int FTP_FILE_TYPE) throws IOException{
		String filename = this.createFileName(partFile);
		boolean createOk = this.myFtp.upload(filename, partFile.getInputStream(), FTP_FILE_TYPE);
		if (createOk) {
			return ResponseEntity.ok(filename);
		}
		return ResponseEntity.badRequest().build();
	}
	
	private String createFileName(MultipartFile partFile) {
		String extend = partFile.getOriginalFilename().substring(partFile.getOriginalFilename().lastIndexOf("."));
		String result = "f_" + UUID.randomUUID().toString() + "_" + Long.toHexString(new Date().getTime()) + extend;
		return result;
	}
	
}
