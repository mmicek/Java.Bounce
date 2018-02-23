package ActionObjects;

import Game.WorldMap;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import java.io.FileNotFoundException;
import java.io.IOException;

public class EnemyBrick extends Objects implements ActionAble {

    public EnemyBrick(WorldMap map, Position position){
        super(map,position.add(new Position(23,15)),null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16 - 15,40 - 15));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() throws IOException {
        map.getBounce().setPosition(map.getSpawnPosition());
        map.getBounce().changeLives(-1);
        map.setMapLevel();
        map.getScreen().setScreen(map.getScreenSpawnPosition().getX());
        map.getBounce().setSize(new Size(Size.Shape.Circle,50,50));
        if(map.getBounce().isDead())
            map.getScreen().quitScreen();
    }
}
