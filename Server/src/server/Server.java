package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    public static void main(String[] args) throws IOException {
        ServerSocket sSocket = new ServerSocket(9001);
        while (true) {
            System.out.println("已就绪");
            try {
                Socket socket = sSocket.accept();
                System.out.println("已连接"+socket.getInetAddress().getHostAddress()+":"+socket.getPort());
                new Thread(new ServerThreadBody(socket)).start();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("客户端连接失败");
                break;
            }
        }
    }
}
