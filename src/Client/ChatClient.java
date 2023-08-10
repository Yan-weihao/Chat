package Client;


import java.awt.*;

public class ChatClient extends Frame {
    public static void main(String[] args) {
        new ChatClient().window();
    }
    public void window(){
       setLocation(300,300);
       this.setSize(400,400);
       setVisible(true);
    }
}
