package Client;

import java.awt.*;

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
       setVisible(true);
       pack();
    }
}
