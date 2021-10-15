package com.ede.edefileservice.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ede.edefileservice.service.FileManagerService;

@RestController
@RequestMapping("/ede-file")
public class FileDeleteRestController {

	@Autowired
	FileManagerService fileManagerService;
	
	@Autowired
	HttpServletResponse httpServletResponse;

	@DeleteMapping("delete/{folder}/{filename}")
	ResponseEntity<Void> deleteFile(@PathVariable("folder") String folder, @PathVariable("filename") String filename) {
		if (null != this.fileManagerService.delete(folder, filename)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.noContent().build();
	}
	
}
