package Client;

import com.sun.xml.internal.ws.api.ha.StickyFeature;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ChatClient extends Frame {


    //用于响应输入事件
    TextField tfTxt = new TextField();
    //用于响应显示事件
    TextArea taContent = new TextArea();
    Socket Cs =null;
    DataOutputStream Dos = null;
    DataInputStream Dis = null;

    Thread tRecv = new Thread(new RvcThread());
    boolean bConnected = false;
    public static void main(String[] args) {
        new ChatClient().window();
    }


    public void window(){
       setLocation(300,300);
       this.setSize(400,400);
       add(tfTxt,BorderLayout.SOUTH);
       add(taContent,BorderLayout.NORTH);
       pack();
       this.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               disconnect();
               System.exit(0);
           }
       });
       tfTxt.addActionListener(new TfListener());
       setVisible(true);
       connect();
       //new Thread(new RvcThread()).start();
        tRecv.start();

    }

    public void connect(){
        try {
            Cs = new Socket("127.0.0.1",8888);
            Dos = new DataOutputStream(Cs.getOutputStream());
            Dis = new DataInputStream(Cs.getInputStream());
            bConnected = true;
        } catch (UnknownHostException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void disconnect(){
        try {
            Dos.close();
            Cs.close();
            Dis.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }
    //响应输入框输入事件
    private class TfListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfTxt.getText().trim();
          //  taContent.setText(s);
            tfTxt.setText("");
            try {
                Dos.writeUTF(s);
                Dos.flush();
            }
            catch (IOException e1 ){
                e1.printStackTrace();

            }
        }
    }

    //创建一个线程用于处理服务端发过来的数据
   private class RvcThread implements Runnable{
        @Override
        public void run() {
            while (bConnected){
                try {
                    String str = Dis.readUTF();
                    System.out.println(str);
                    taContent.setText(taContent.getText()+str+'\n');
                } catch (SocketTimeoutException e){
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
