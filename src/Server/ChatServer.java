package Server;

import com.sun.deploy.util.SessionState;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    List <Client> clients = new ArrayList<Client>();//存放线程
    public static void main(String[] args) {
        new ChatServer().Stata();

    }
    public void Stata (){

        boolean started = false;
        ServerSocket Ss =null;
        try {
            Ss = new ServerSocket(8888);
        }
        catch (BindException e) {
            System.out.println("端口使用中.....");
            System.out.println("切换窗口");
            System.exit(-1);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        try{
            started = true;
            while (started){
                boolean bConnected = false;
                Socket s= Ss.accept();
                Client c = new Client(s);
                new Thread(c).start();//启动线程
                clients.add(c);// 将启动的线程存放到集合里面
            }
        } catch (EOFException e) {
            System.out.println("Client closed! ");
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                Ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    class Client implements Runnable{
        private Socket Ss;
        private DataInputStream dis;
        private DataOutputStream dou;
        private boolean bConnected = false;
        Client(Socket Ss){
            try {
                bConnected = true;
                dis = new DataInputStream(Ss.getInputStream());
                dou = new DataOutputStream(Ss.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        void Send (String str){
            try {
                dou.writeUTF(str);
                dou.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @Override
        public void run() {
            try {
                while (bConnected){
                    String str = dis.readUTF();
                   System.out.println("clients.size :"+ clients.size());
                    for (int i = 0; i < clients.size(); i++) {
                       Client c = clients.get(i);
                        System.out.println(str);
                        c.Send(str); //数据转发
                    }
                }
            }catch (EOFException e){
                System.out.println("有一个客户端断开连接！");
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try {
                    if(dis != null) dis.close();
                   // if(Ss != null)  Ss.close();
                    if(dou != null) dou.close();
                }
                catch (IOException e1){
                    e1.printStackTrace();
                }
            }

        }
    }
}

