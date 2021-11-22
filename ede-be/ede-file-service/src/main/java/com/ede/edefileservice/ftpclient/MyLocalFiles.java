package com.ede.edefileservice.ftpclient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MyLocalFiles {

	public String saveFile(String name, byte[] data) throws IOException {
		File file = new File("assets/" + name);
		if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		Files.write(file.toPath(), data);
		return name;
	}

	public byte[] readFile(String name) throws IOException {
		File file = new File("assets/" + name);
		return Files.readAllBytes(file.toPath());
	}
	
	public File getFile(String name) {
		return new File("assets/" + name);
	}
	
	public boolean deleteFile(String name) {
		File file = new File("assets/" + name);
		try {
			Files.delete(file.toPath());
			return true;
		} catch (IOException e) {
			System.err.println(e);
			return false;
		}
	}
	
}
