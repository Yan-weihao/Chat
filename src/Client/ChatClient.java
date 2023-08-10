package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ChatClient extends Frame {
    //用于响应输入事件
    TextField tfTxt = new TextField();
    //用于响应显示事件
    TextArea taContent = new TextArea();
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
               System.exit(0);
           }
       });
       tfTxt.addActionListener(new TfListener());
       setVisible(true);
    }
    private class TfListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = tfTxt.getText().trim();
            taContent.setText(s);
            tfTxt.setText("");
            connect(s);
        }
    }
    public void connect(String s){
        try {
            Socket Cs = new Socket("127.0.0.1",8888);
            OutputStream os = Cs.getOutputStream();
            DataOutputStream Dos = new DataOutputStream(os);
            Dos.writeUTF(s);
            Dos.flush();
            Dos.close();
            Cs.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
