package skate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
//import java.awt.List; -> gruba sprawa pomylka z import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Road extends JPanel implements ActionListener, Runnable {
    
    Timer mainTimer = new Timer(20, this);
    Image img = new ImageIcon("src/res/images/background.png").getImage();
    Player p = new Player();
    Thread enemiesFactory = new Thread(this);
    List<Enemy> enemies = new ArrayList<Enemy>();
    
    public Road() {
        mainTimer.start();
        enemiesFactory.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }

    @Override
    public void run() {
        while(true){
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(1200, rand.nextInt(600), rand.nextInt(60), this));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            p.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            p.keyReleased(e);
        }
    }
    
    public void paint(Graphics g) {
        g = (Graphics2D) g;
        g.drawImage(img, p.layer1, 0, null);
        g.drawImage(img, p.layer2, 0, null);
        g.drawImage(p.img, p.x, p.y, null);
        
        //speed_counter
        double v = (50/Player.MAX_V) * p.v;
        g.drawString("Speed: "+v+" kph", 100, 30);
        
        Iterator<Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy e = i.next();
            if (e.x >= 2400 || e.x <= -2400) {
                i.remove();
            } else {
                e.move();
                g.drawImage(e.img, e.x, e.y, null);
                        }
            g.drawImage(e.img, e.x, e.y, null);
            }
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        p.move();
        repaint();
        testCollisionWithEnemiers();
        testWin();
    }


    private void testWin() {
        if (p.s > 20000) {
            JOptionPane.showMessageDialog(null, "You win!!! xD");
            System.exit(0);
        }
    }

    
    private void testCollisionWithEnemiers() {
        Iterator<Enemy> i = enemies.iterator();
        while (i.hasNext()) {
            Enemy e = i.next();
            if (p.getRect().intersects(e.getRect())) {
                JOptionPane.showMessageDialog(null, "You loose!");
                System.exit(1);
            }
        }
    }

    

}
