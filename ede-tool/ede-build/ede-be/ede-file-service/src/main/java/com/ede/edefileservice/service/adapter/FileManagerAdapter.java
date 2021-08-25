package com.ede.edefileservice.service.adapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ede.edefileservice.service.FileManagerService;

/**
 * File manager using for reading file and save the file
 * @author vinh
 *
 */
@Service
public class FileManagerAdapter implements FileManagerService {
	
	@Autowired
	private ServletContext context;
	
	@Override
	public byte[] read(String folder, String fileName) throws IOException {
		File directory = new File(this.context.getRealPath(folder));
		File diskFile = new File(directory.getAbsolutePath() + "\\" + fileName);
		System.out.println(diskFile.getAbsolutePath());
		return Files.readAllBytes(diskFile.toPath());
	}
	
	@Override
	public File save(String folder, MultipartFile multipartFile) throws IOException {
		File directory = new File(this.context.getRealPath(folder));
		String fileName = "f_" + UUID.randomUUID().toString() + "_" + Long.toHexString(new Date().getTime()) + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
		File diskFile = new File(directory.getAbsolutePath() + "\\" + fileName);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		multipartFile.transferTo(diskFile);
		return diskFile;
	}
	
	@Override
	public File delete(String folder, String filename) {
		File directory = new File(this.context.getRealPath(folder));
		File diskFile = new File(directory.getAbsolutePath() + "\\" + filename);
		if (diskFile.delete()) {
			return diskFile;
		}
		return null;
	}
}
