package Server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        Socket s = null;
        boolean started = false;
        DataInputStream dis = null;
        ServerSocket Ss =null;
        try {
            Ss = new ServerSocket(8888);
        }
        catch (BindException e) {
            System.out.println("sss.....");
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try{
             started = true;
            while (started){
                boolean bConnected = false;
                s= Ss.accept();
                bConnected =true;
                dis = new DataInputStream(s.getInputStream());
                while (bConnected){
                    String str = dis.readUTF();
                    System.out.println(str);
                }
            }
        } catch (EOFException e) {
            System.out.println("Client closed! ");
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(dis != null) dis.close();
                if(Ss != null)  Ss.close();
            }
            catch (IOException e1){
                e1.printStackTrace();
            }
        }

    }
}
