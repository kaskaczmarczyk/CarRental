import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    public MyFrame() {
        super("Login");
        LoginListener listener = new LoginListener(this);
        JPanel loginPanel = new LoginPanel(listener);
        add(loginPanel);

        setPreferredSize(new Dimension(350, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

/*    public void loginAction() {
        MyFrame myFrame = new MyFrame();
        myFrame.setSize(280, 250);
        myFrame.setLocation(300, 300);
        myFrame.setResizable(false);
        myFrame.setAlwaysOnTop(true);
        myFrame.setLayout(null);
        myFrame.setVisible(true);
        JTextArea area = new JTextArea();
        JScrollPane scroll = new JScrollPane(area);
        area.setBounds(5, 5, 265, 210);
        myFrame.add(area);
    }*/
}
