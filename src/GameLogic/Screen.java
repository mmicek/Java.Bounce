package GameLogic;

import Game.Bounce;
import Game.Enemy;
import Game.StartGame;
import GameObjects.RingBig;
import GameObjects.RingSmall;
import Settings.Position;
import Game.WorldMap;
import GameObjects.Objects;
import Settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class Screen extends JFrame {
    /**
     * Diry painting moze byc spowodawany tym ze zamiast size jest float ?
     */
    private WorldMap map;
    private Image doubleBuffer;
    private Position position = new Position(0,0);
    private Image background = new ImageIcon("background.jpg").getImage();

    public Screen() throws InvocationTargetException, InterruptedException {
        setUndecorated(true);
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
    }

    public void setMap(WorldMap map){
        this.map = map;
        addKeyListener(new KeyMenager(this,map));
    }

    public synchronized void quitScreen() throws IOException {
        dispose();
    }

    public void test(){
        BufferStrategy strategy = getBufferStrategy();
        Graphics g = strategy.getDrawGraphics();
        paint(g);
        g.dispose();
        strategy.show();
    }

    public void draw(){
        if(doubleBuffer == null)
            doubleBuffer = createImage(getWidth(),getHeight());
        if(doubleBuffer != null){
            Graphics g2 = doubleBuffer.getGraphics();
            paint(g2);
            g2.dispose();
            getGraphics().drawImage(doubleBuffer,0,0,null);
        }
        else{
           repaint();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        ArrayList<Objects> objects = map.getObjectsToPaint();
        g.drawImage(background,0,0,(int)GameSettings.realWidth + 1,(int)GameSettings.realHight + 1,null);
        for(int i=0;i<objects.size();i++) {
            if(objects.get(i) instanceof Enemy)
                g.drawImage(objects.get(i).getImage(), objects.get(i).getPosition().getX() - position.getX(), objects.get(i).getPosition().getY(), objects.get(i).getSize().getWidth(), objects.get(i).getSize().getHight(), null);
            else
                g.drawImage(objects.get(i).getImage(), objects.get(i).getPosition().getX() - position.getX(), objects.get(i).getPosition().getY(), objects.get(i).getSize().getWidth(), objects.get(i).getSize().getHight(), null);
        }
        g.drawImage(map.getBounce().getImage(),map.getBounce().getPosition().getX()-position.getX(),map.getBounce().getPosition().getY(),map.getBounce().getSize().getWidth(),map.getBounce().getSize().getHight(),null);
    }

    public void setScreen(int x){
        position = new Position(x,0);
        if(x < 0)
            position = new Position(0,0);   //dorobic zeby nie  przesuwalo sie za daleko w prawo mapa
        else if(x > map.getMapLength())
            position = new Position(map.getMapLength() - GameSettings.realHight,0);
    }

    public Position getPosition(){
        return position;
    }
}
