package com.ede.edefileservice.service.adapter;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.ede.edefileservice.ftpclient.MyFTPClient;
import com.ede.edefileservice.service.FTPClientManager;

@Service
public class FTPClientManagerAdapter implements FTPClientManager {

	@Override
	public String[] getListNames() {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			return ftpClient.listNames();
		}
		catch (Exception e) {
			System.err.println(e);
		}
		return new String[0];
	}
	
	@Override
	public InputStream getFile(String nameInRemote, int FTP_FILE_TYPE) {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			ftpClient.setFileType(FTP_FILE_TYPE);
			return ftpClient.retrieveFileStream(nameInRemote);
		}
		catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public boolean upload(String nameInRemote, InputStream fileContent, int FTP_FILE_TYPE) {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			ftpClient.setFileType(FTP_FILE_TYPE);
			return ftpClient.storeFile(nameInRemote, fileContent);
//			return ftpClient.appendFile(nameInRemote, fileContent);
		}
		catch (Exception e) {
			System.err.println(e);
		}
		return false;
	}
	
	@Override
	public boolean delete(String nameInRemote) {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			return ftpClient.deleteFile(nameInRemote);
		}
		catch(Exception e) {
			System.err.println(e);
		}
		return false;
	}

	@Override
	public boolean update(String nameInRemote, InputStream inputStream, int FTP_FILE_TYPE) {
		boolean isDelete = this.delete(nameInRemote);
		if (isDelete) {
			return this.upload(nameInRemote, inputStream, FTP_FILE_TYPE);
		}
		return false;
	}
	
}