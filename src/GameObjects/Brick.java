package GameObjects;

import Game.*;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

public class Brick extends Objects {
    public Brick(WorldMap map, Position position){
        super(map,position,null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }
}
