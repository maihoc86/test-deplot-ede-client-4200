package com.ede.edefileservice.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

@Service
public interface FTPClientManager {

	String[] getListNames();

	InputStream getFile(String nameInRemote, int FTP_FILE_TYPE);

	boolean upload(String nameInRemote, InputStream fileContent, int FTP_FILE_TYPE);

	boolean delete(String nameInRemote);

	boolean update(String nameInRemote, InputStream fileContent, int fTP_FILE_TYPE);
	
}
