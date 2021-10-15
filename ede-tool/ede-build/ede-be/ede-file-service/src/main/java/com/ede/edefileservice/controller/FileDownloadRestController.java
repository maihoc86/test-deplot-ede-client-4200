package com.ede.edefileservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edefileservice.service.FileManagerService;

@RestController
@RequestMapping("/ede-file")
public class FileDownloadRestController {
	
	@Autowired
	FileManagerService fileManagerService;
	
	@Autowired
	HttpServletResponse httpServletResponse;
	
	@GetMapping("get/{folder}/{filename}")
	ResponseEntity<byte[]> getFile(@PathVariable("folder") String folder, @PathVariable("filename") String filename) {
		try {
			this.httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+filename);
			return ResponseEntity.ok(this.fileManagerService.read(folder, filename));
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
