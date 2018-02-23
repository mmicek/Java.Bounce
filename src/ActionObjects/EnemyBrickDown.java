package ActionObjects;

import ActionObjects.EnemyBrick;
import Game.WorldMap;
import Settings.Position;

public class EnemyBrickDown extends EnemyBrick {
    public EnemyBrickDown(WorldMap map, Position position) {
        super(map, position);
        setImageIcon(getImageIcon(this));
        setPosition(getPosition().add(new Position(0,-15)));
    }
}
