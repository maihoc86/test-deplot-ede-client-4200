package com.ede.edefileservice.service.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ede.edefileservice.ftpclient.MyLocalFiles;
import com.ede.edefileservice.service.FTPClientManager;

@Primary
@Service
public class LocalFileManagerAdapter implements FTPClientManager {

	private MyLocalFiles localFile = new MyLocalFiles();
	
	@Override
	public String[] getListNames() {
		System.err.println("> Không hổ trợ chức năng này, api đã được xoá");
		return new String[0];
	}

	@Override
	public InputStream getFile(String nameInRemote, int FTP_FILE_TYPE) {
		try {
			return new FileInputStream(localFile.getFile(nameInRemote));
		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public boolean upload(String nameInRemote, InputStream fileContent, int FTP_FILE_TYPE) {
		try {
			localFile.saveFile(nameInRemote, fileContent.readAllBytes());
			return true;
		} catch (IOException e) {
			System.err.println(e);
		}
		return false;
	}

	@Override
	public boolean delete(String nameInRemote) {
		return localFile.deleteFile(nameInRemote);
	}

	@Override
	public boolean update(String nameInRemote, InputStream fileContent, int fTP_FILE_TYPE) {
		try {
			localFile.deleteFile(nameInRemote);
			localFile.saveFile(nameInRemote, fileContent.readAllBytes());
			return true;
		} catch (IOException e) {
			System.err.println(e);
		}
		return false;
	}

	@Override
	public List<String> uploadMulti(Map<String, InputStream> mapInputStream, int FPT_FILE_TYPE) {
		List<String> result = new ArrayList<String>();
		mapInputStream.forEach((key, stream) -> {
			try {
				localFile.saveFile(key, stream.readAllBytes());
				result.add(key);
			} 
			catch (IOException e) {
				System.err.println(e);
			}
		});
		return result;
	}

	@Override
	public List<String> deleteMulti(String[] filenames) {
		List<String> result = new ArrayList<String>();
		for (String name : filenames) {
			if (localFile.deleteFile(name)) {
				result.add(name);
			}
		}
		return result;
	}
	
}