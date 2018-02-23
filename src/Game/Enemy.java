package Game;

import GameLogic.Velocity;
import GameObjects.ActionAble;
import GameObjects.MobileObjects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Enemy extends MobileObjects implements ActionAble {
    private Position from;
    private Position to;

    Enemy(WorldMap map, Position position,Position from,Position to) {
        super(map,position,new Velocity((to.getX()-from.getX())/4000f,(to.getY()-from.getY())/4000f)
                ,new Size(Size.Shape.Rect,2*GameSettings.realHight/16,2*GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
        this.from = from;
        this.to = to;
    }

    public void update(long elapsedTime){
        if(!position.isBetween(from,to)) {
            if(position.isCloser(from,to))
                velocity = new Velocity((to.getX()-from.getX())/4000f,(to.getY()-from.getY())/4000f);
            else
                velocity = new Velocity(-(to.getX()-from.getX())/4000f,-(to.getY()-from.getY())/4000f);
        }
        super.update(elapsedTime,this.velocity);
    }

    @Override
    public void executeAction() throws IOException {
        map.getBounce().setPosition(map.getSpawnPosition());
        map.getScreen().setScreen(map.getScreenSpawnPosition().getX());
        map.getBounce().changeLives(-1);
        map.setMapLevel();
        map.getBounce().setSize(new Size(Size.Shape.Circle,50,50));
        if(map.getBounce().isDead())
            map.getScreen().quitScreen();
    }
}
