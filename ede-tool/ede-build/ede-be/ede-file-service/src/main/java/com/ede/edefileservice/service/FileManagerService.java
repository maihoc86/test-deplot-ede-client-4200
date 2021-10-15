package com.ede.edefileservice.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileManagerService {

	byte[] read(String folder, String filename) throws IOException;

	File save(String folder, MultipartFile file) throws IOException;

	File delete(String folder, String filename);

}
