package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;

public class MiniMenu extends JFrame {
    private Container c = getContentPane();

    public MiniMenu(int maxLevels) throws InvocationTargetException, InterruptedException {
        setUndecorated(true);
        c.setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        try {
            EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    createBufferStrategy(2);
                }
            });
        }catch(Exception e){ ; }
        setIgnoreRepaint(true);

        for(int i=0;i<maxLevels;i++){
            final int I = i + 1;
            JLabel label = new JLabel();
            JButton button = new JButton(new AbstractAction("Level " + I) {
                public void actionPerformed(ActionEvent e) {
                    StartGame.setLevel(I);

                    dispose();
                }
            });
            label.add(button);
            c.add(label);
            label.setBounds(800,i*200 + 100,300,150);
            button.setSize(label.getSize());
        }
    }
}
