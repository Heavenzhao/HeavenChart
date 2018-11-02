package com.heaven;
import java.io.*;
import java.net.Socket;

/**
 * 客户端
 */
public class ChartClient {

    private static int DEFAULT_SERVER_PORT = 8848;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";

    public static void main(String[] args) throws IOException{
        Socket socket = new Socket(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT);
        new Thread(new ClientHandler(socket)).start();
        PrintStream ps = new PrintStream(socket.getOutputStream());
        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while((line = br.readLine()) != null) {
            ps.println(line);
        }
    }
}
