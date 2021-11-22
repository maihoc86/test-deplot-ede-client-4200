package com.ede.edefileservice.service.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import com.ede.edefileservice.ftpclient.MyFTPClient;
import com.ede.edefileservice.service.FTPClientManager;

/**
 * Một số máy tính không chạy được lớp này, không rõ lý do
 * Sử dụng {@link LocalFileManagerAdapter} thay thế
 * @author vinh
 *
 */
@Deprecated
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

	@Override
	public List<String> uploadMulti(Map<String, InputStream> mapInputStream, int FPT_FILE_TYPE) {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			ftpClient.setFileType(FPT_FILE_TYPE);
			List<String> result = new ArrayList<String>();
			mapInputStream.forEach((key, part) -> {
				try {
					ftpClient.storeFile(key, mapInputStream.get(key));
					result.add(key);
				} catch (IOException e) {
					System.err.println(e);
				}
			});
			return result;
		}
		catch(Exception e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public List<String> deleteMulti(String[] filenames) {
		try (MyFTPClient ftp = new MyFTPClient()) {
			FTPClient ftpClient = ftp.open();
			List<String> result = new ArrayList<String>();
			for (String filename: filenames) {
				if (ftpClient.deleteFile(filename)) {
					result.add(filename);
				}
			}
			return result;
		}
		catch(Exception e) {
			System.err.println(e);
		}
		return null;
	}
	
}