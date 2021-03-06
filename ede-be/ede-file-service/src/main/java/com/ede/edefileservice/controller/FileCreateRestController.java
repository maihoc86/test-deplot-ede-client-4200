package com.ede.edefileservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Lớp dùng để upload dữ liệu lên kho lưu trử
 * 
 * @author Vinh
 *
 */
@RestController
@RequestMapping("/ede-file")
public class FileCreateRestController {

	@Autowired
	FTPClientManager myFtp;

	@PostMapping("create-multi/binary")
	ResponseEntity<List<String>> putFiles(@RequestPart("files") MultipartFile[] partFiles) throws IOException {
		return addFiles(partFiles, FTP.BINARY_FILE_TYPE);
	}

	@PostMapping("create/binary")
	public ObjectNode putFile(@RequestPart("file") MultipartFile partFile) throws IOException {
		return addFile(partFile, FTP.BINARY_FILE_TYPE);
	}

	private ObjectNode addFile(MultipartFile partFile, int FTP_FILE_TYPE) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		String filename = this.createFileName(partFile);
		objectNode.put("text", filename);
		
		boolean createOk = this.myFtp.upload(filename, partFile.getInputStream(), FTP_FILE_TYPE);
		if (createOk) {
			return objectNode;
		}
		return null;
	}

	private ResponseEntity<List<String>> addFiles(MultipartFile[] partFiles, int FTP_FILE_TYPE) throws IOException {
		Map<String, InputStream> mapPart = new HashMap<String, InputStream>();
		for (MultipartFile partFile : partFiles) {
			String fileName = createFileName(partFile);
			mapPart.put(fileName, partFile.getInputStream());
		}

		List<String> ls = this.myFtp.uploadMulti(mapPart, FTP_FILE_TYPE);
		System.err.println(ls);
		if (null != ls && !ls.isEmpty()) {
			return ResponseEntity.ok(ls);
		}

		return ResponseEntity.badRequest().build();
	}

	private String createFileName(MultipartFile partFile) {
		String extend = partFile.getOriginalFilename().substring(partFile.getOriginalFilename().lastIndexOf("."));
		String result = "f_" + Long.toHexString(new Date().getTime()) + "_" + UUID.randomUUID().toString() + extend;
		return result;
	}

}
