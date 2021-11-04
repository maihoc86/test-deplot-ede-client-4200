package com.ede.edefileservice.ftpclient;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class MyFTPClient implements Closeable {
	
	private String server = "ftp.pkeuutew.000nethost.com";
    private int port = 21;
    private String user = "edarkeye@pkeuutew.000nethost.com";
    private String password = "Admin0123/";
	private FTPClient ftp;
	
	public FTPClient open() throws SocketException, IOException {
		ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);
        return ftp;
	}

	@Override
	public void close() throws IOException {
		ftp.disconnect();
	}
	
}
