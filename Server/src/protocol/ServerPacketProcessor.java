package protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerPacketProcessor {
    public Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ServerPacketProcessor(Socket socket) throws IOException {
        this.socket=socket;
        outputStream=new ObjectOutputStream(socket.getOutputStream());
        inputStream=new ObjectInputStream(socket.getInputStream());
    }

    public Request receiveRequest(){
        try {
            Request request=(Request) inputStream.readObject();
            System.out.println("接收到请求"+request.getUser()+":"+request.getOperator());
            return request;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendResponse(Response response){
        try {
            outputStream.writeObject(response);
            System.out.println("发送响应"+response.getUserName()+":"+response.getOperator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
