package ActionObjects;

import Game.WorldMap;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

public class CancelBonus extends Objects implements ActionAble {
    public CancelBonus(WorldMap map, Position position) {
        super(map,position,null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        if(map.getBounce().isBig())
            map.getBounce().cancelBonus();
    }
}
