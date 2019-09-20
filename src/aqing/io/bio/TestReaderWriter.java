package aqing.io.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestReaderWriter {

    public static void main(String args[]){
        testHandleBIO();
    }

    //blocking input output.
    public static void testHandleBIO(){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(7777);//创建网络槽口通道监听7777端口，没有权限的情况下需要大于1024
            Socket clientSocket = serverSocket.accept();//阻塞方法，直到连接建立(tcp三次握手)，返回client和server之间的通信
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            String request, response;
            while((request = in.readLine()) != null){
                if("Done".equals(request)){
                    break;
                }
                response = processRequest(request);
                System.out.print(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String processRequest(String request){
        return "123test";
    }
}
