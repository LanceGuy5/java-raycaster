package window;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

@Deprecated
public class SideMap {
    
    public SideMap(Dimension d, String name, Component c){
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setIconImage(null); //TODO

        frame.setSize(d);
        frame.setPreferredSize(d);
        frame.setMinimumSize(d);
        frame.setMaximumSize(d);
        frame.setResizable(false);

        frame.setAutoRequestFocus(true);
        frame.setLocationRelativeTo(null);
        frame.add(c);
        frame.pack();
        frame.setVisible(false);
    }

}
