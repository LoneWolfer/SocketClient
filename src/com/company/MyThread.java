package com.company;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

public class MyThread extends Thread{
    private Socket socket = null;
    private Reader reader = null;
    private int len = 0;
    char[] chars = new char[64];
    private Client client = null;
    private String temp = "";

    public MyThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            reader = new InputStreamReader(socket.getInputStream());
        } catch (Exception e) {
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("客户端子线程"+this.getId()+"开始工作");
        while (true) {
            try {
                if (socket.isClosed() == false) {
                    if (socket.isInputShutdown() == false) {
                        while ((len = ((Reader) reader).read(chars)) != -1) {
                            temp = "服务器说——>"+":"+ new String(chars, 0, len);
                            client.appendMsg(temp);
                            System.out.println();
                        }
                    }

                } else {
                    if (socket.getKeepAlive() == false) {
                        reader.close();
                        socket.close();
                        this.stop();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
