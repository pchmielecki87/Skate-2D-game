package skate;

import javax.swing.*;

public class Skate {

    public static void main(String[] args) {

        JFrame f = new JFrame("Java skate");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1350, 600);
        f.add(new Road());
        f.setVisible(true);
        

    }
    
}
