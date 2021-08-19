package com.ede.edefileservice.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ede.edefileservice.service.FileManagerService;

@RestController
@RequestMapping("/ede-file")
public class FileUploadRestController {

	@Autowired
	FileManagerService fileManagerService;
	
	@Autowired
	HttpServletResponse httpServletResponse;

	@PostMapping("save/{folder}")
	ResponseEntity<String> save(@PathVariable("folder") String folder, @RequestPart("file") MultipartFile file) {
		try {
			File fileDone = this.fileManagerService.save(folder, file);
			//XXX Kiểm tra lại đường dẫn khi tạo thành công
			return ResponseEntity.created(URI.create("/ede-file/get/" + folder + "/" + fileDone.getName())).body(folder + fileDone.getName());
		} catch (IOException e) {
			return ResponseEntity.noContent().build();
		}
	}
	
}
