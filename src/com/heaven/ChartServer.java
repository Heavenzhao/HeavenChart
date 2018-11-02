package com.heaven;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 服务端
 */
public class ChartServer {

    //默认的端口号
    private static int DEFAULT_PORT = 8848;
    public static List<Socket> clientList = new ArrayList<Socket>();

    //单例的ServerSocket
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException{
        start();
    }

    //根据传入参数设置监听端口，如果没有参数调用以下方法并使用默认值
    public static void start() throws IOException {
        //使用默认值
        start(DEFAULT_PORT);
    }
    //这个方法不会被大量并发访问，不太需要考虑效率，直接进行方法同步就行了
    public synchronized static void start(int port) throws IOException {
        if (serverSocket != null) return;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端已启动，端口号:" + port);
            while (true) {
                Socket socket = serverSocket.accept();
                clientList.add(socket);
                new Thread(new ServerHandler(socket)).start();
            }
        } finally {
            if (serverSocket != null) {
                System.out.println("服务端已关闭。");
                serverSocket.close();
                serverSocket = null;
            }
        }
    }
}
