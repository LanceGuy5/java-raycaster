package window;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

    JFrame frame;

    public Window(Dimension d, String name, Component c){
        frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setIconImage(null); //TODO

        // frame.setSize(d);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setAutoRequestFocus(true);
        frame.setLocationRelativeTo(null);
        frame.add(c);
        // frame.pack();
        frame.setVisible(true);
    }

    public Dimension getFrameDimensions(){
        return new Dimension(frame.getWidth(), frame.getHeight());
    }

}
