package com.ede.edefileservice.controller;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ede.edefileservice.service.FTPClientManager;

/**
 * Lớp dùng để update dữ liệu trên FTP SERVER
 * @author Vinh
 *
 */
@RestController
@RequestMapping("/ede-file")
public class FileUpdateRestController {

	@Autowired
	private FTPClientManager myFtp;
	
	@PutMapping("update/binary/{filename}")
	ResponseEntity<String> putFile(@PathVariable("filename") String filename, @RequestPart("file") MultipartFile partFile) throws IOException{
		System.err.println(partFile);
		return updateFile(filename, partFile, FTP.BINARY_FILE_TYPE);
	}
	
	private ResponseEntity<String> updateFile(String filename, MultipartFile partFile, int FTP_FILE_TYPE) throws IOException{
		boolean isUpdate = this.myFtp.update(filename, partFile.getInputStream(), FTP_FILE_TYPE);
		if (isUpdate) {
			return ResponseEntity.ok(filename);
		}
		return ResponseEntity.badRequest().build();
	}
	
}
