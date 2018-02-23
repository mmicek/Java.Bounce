package ActionObjects;

import Game.WorldMap;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

public class BiggerBallBonus extends Objects implements ActionAble {

    public BiggerBallBonus(WorldMap map, Position position){
        super(map,position.add(new Position(23,17)),null);
        setSize(new Size(Size.Shape.Rect,52 ,22));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        map.getBounce().setBigger();
    }
}
