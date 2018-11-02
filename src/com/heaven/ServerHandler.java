/**
 * 客户端消息处理线程
 */
package com.heaven;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerHandler implements Runnable {

    private Socket socket;
    BufferedReader in = null;

    public ServerHandler(Socket socket) throws IOException{
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run()  {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            String msg;
            while (true) {
                if ((msg = readFromClient()) == null) break;
                System.out.println(("服务端收到信息：" + msg));
                for(Socket s:ChartServer.clientList) {
                    if(s != this.socket){
                        out = new PrintWriter(s.getOutputStream(), true);
                        out.println(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
        }
    }

    private String readFromClient() {
        try {
            return in.readLine();
        }catch (IOException e) {
            ChartServer.clientList.remove(socket);
            e.printStackTrace();
        }
        return null;
    }
}
