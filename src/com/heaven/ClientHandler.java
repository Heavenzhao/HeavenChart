package com.heaven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket s;
    BufferedReader br;

    public ClientHandler(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String context = null;
            while((context = br.readLine()) != null) {
                System.out.println("服务器返回的消息："+ context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
