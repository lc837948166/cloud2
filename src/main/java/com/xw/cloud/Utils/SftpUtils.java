package com.xw.cloud.Utils;

import com.jcraft.jsch.*;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

import java.io.InputStream;

@Log
public class SftpUtils {

    private static ChannelSftp channel;
    private static Session session;

    // Connection
    @SneakyThrows
    public static ChannelSftp getSftpcon() {

            String remoteHost = "127.0.0.1";
            String username = "root";
            String password = "111";

            // 创建 SSH 连接
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, remoteHost, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            ChannelSftp channel;
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

        return channel;
    }

    @SneakyThrows
    public static String getexecon(String commd) {

        String remoteHost = "127.0.0.1";
        String username = "root";
        String password = "111";

        // 创建 SSH 连接
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, remoteHost, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel=session.openChannel("exec");
        ((ChannelExec) channel).setCommand(commd);
        InputStream in;
        in = channel.getInputStream();  // 获取命令执行结果的输入流
        channel.connect();
        in = channel.getInputStream();  // 获取命令执行结果的输入流
        channel.connect();  // 连接远程执行命令
        byte[] tmp = new byte[1024];
        StringBuilder commandOutput = new StringBuilder(); //存储命令执行的输出
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                commandOutput.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                break;
            }
        }


        return commandOutput.toString();
    }

    @SneakyThrows
    public static String getexecon1(String ipaddr,String commd) {

        String remoteHost = ipaddr;
        String username = "root";
        String password = "806592LClc.";

        // 创建 SSH 连接
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, remoteHost, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        Channel channel=session.openChannel("exec");
        ((ChannelExec) channel).setCommand(commd);
        InputStream in;
        in = channel.getInputStream();  // 获取命令执行结果的输入流
        channel.connect();
        in = channel.getInputStream();  // 获取命令执行结果的输入流
        channel.connect();  // 连接远程执行命令
        byte[] tmp = new byte[1024];
        StringBuilder commandOutput = new StringBuilder(); //存储命令执行的输出
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                commandOutput.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0) continue;
                break;
            }
        }


        return commandOutput.toString();
    }


    public static void discon(){
            if(channel!=null) channel.disconnect();
            if(session!=null) session.disconnect();

    }

}
