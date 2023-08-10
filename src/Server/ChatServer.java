package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {

        try {
            ServerSocket Ss = new ServerSocket(8888);

            while (true){
                Socket s= Ss.accept();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                System.out.println(dis.readUTF());
;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
