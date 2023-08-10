package Server;

import com.sun.corba.se.spi.activation.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static void main(String[] args) {

        try {
            ServerSocket Ss = new ServerSocket(8888);

            while (true){
                Socket s= Ss.accept();
                System.out.println("a client connected!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
