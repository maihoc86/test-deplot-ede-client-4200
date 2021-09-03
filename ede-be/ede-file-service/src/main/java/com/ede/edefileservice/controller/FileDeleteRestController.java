package com.ede.edefileservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edefileservice.service.FTPClientManager;

/**
 * Lớp dùng để xóa file từ FTP Server
 * @author Vinh
 *
 */
@RestController
@RequestMapping("/ede-file")
public class FileDeleteRestController {
	
	@Autowired
	FTPClientManager ftpClientManager;

	@DeleteMapping("delete/{filename}")
	ResponseEntity<Boolean> deleteFile(@PathVariable("filename") String filename){
		boolean isRemove = this.ftpClientManager.delete(filename);
		return ResponseEntity.ok(isRemove);
	}
	
	// XXX DeleteMapping not have body
	@PostMapping("delete-multi")
	ResponseEntity<List<String>> deleteFile(@RequestBody String[] filenames){
		System.err.println("Vào đây" + filenames.toString());
		List<String> removed = this.ftpClientManager.deleteMulti(filenames);
		return ResponseEntity.ok(removed);
	}
	
}
