package protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientPacketProcessor {
    Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;

    public ClientPacketProcessor(Socket socket) throws IOException {
        this.socket=socket;
        inputStream=new ObjectInputStream(socket.getInputStream());
        outputStream=new ObjectOutputStream(socket.getOutputStream());
    }

    public int sendRequest(Request request){
        try {
            outputStream.writeObject(request);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public Response receiveResponse(){
        try {
            Response response=(Response) inputStream.readObject();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
