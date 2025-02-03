package com.kb.inno.common;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

public class SFTPDownloader {

    private final String host;
    private final int port;
    private final String username;
    private final String password;

    public SFTPDownloader(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public void downloadFile(String remoteFilePath, String localFilePath) {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 호스트 키 검사를 비활성화합니다. 실제 환경에서는 보안을 위해 이 설정을 변경해야 합니다.
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();

            if (!fileExists(channelSftp, remoteFilePath)) {
                return;
            }



            channelSftp.get(remoteFilePath, localFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public boolean isServerReachable(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean fileExists(ChannelSftp sftpChannel, String remoteFilePath) {
        try {
            sftpChannel.lstat(remoteFilePath);
            return true;
        } catch (SftpException e) {
            return false;
        }
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
