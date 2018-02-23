package GameLogic;

import Settings.Direction;
import Game.WorldMap;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class KeyMenager implements KeyListener {
    private Screen screen;
    private WorldMap map;
    private int numberOfKeys = 0;
    private int lastPressedKey = -1;
    private int lastRealisedKey = -1;
    private boolean jumpButton = false;

    KeyMenager(Screen screen,WorldMap map) {
        this.map = map;
        this.screen = screen;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_ESCAPE:
                try {
                    screen.quitScreen();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(lastPressedKey != key || lastPressedKey == -1) {
                    numberOfKeys++;
                    lastPressedKey = key;
                }
                map.getBounce().startMoving(Direction.Left);
                break;
            case KeyEvent.VK_RIGHT:
                if(lastPressedKey != key || lastPressedKey == -1) {
                    numberOfKeys++;
                    lastPressedKey = key;
                }
                map.getBounce().startMoving(Direction.Right);
                break;
            case KeyEvent.VK_SPACE:
                map.getBounce().setJump();
                if(!jumpButton) {
                    map.getBounce().setJumpBoost(1);
                    jumpButton = true;
                }
                break;
            case KeyEvent.VK_UP:
                map.getBounce().setJump();
                if(!jumpButton) {
                    map.getBounce().setJumpBoost(1);
                    jumpButton = true;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_ESCAPE:
                try {
                    screen.quitScreen();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case KeyEvent.VK_LEFT:
                if(lastRealisedKey != key || lastRealisedKey == -1) {
                    numberOfKeys--;
                    lastRealisedKey = key;
                }
                exchange(Direction.Right);
                break;
            case KeyEvent.VK_RIGHT:
                if(lastRealisedKey != key || lastRealisedKey == -1) {
                    numberOfKeys--;
                    lastRealisedKey = key;
                }
                exchange(Direction.Left);
                break;
            case KeyEvent.VK_UP:
                map.getBounce().stopJump();
                jumpButton = false;
                break;
            case KeyEvent.VK_SPACE:
                map.getBounce().stopJump();
                jumpButton = false;
        }
    }

    private void exchange(Direction direction){
        if(numberOfKeys == 1)
            map.getBounce().startMoving(direction);
        else
            map.getBounce().stopMoving();
    }

}
