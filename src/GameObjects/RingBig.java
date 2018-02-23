package GameObjects;

import Game.WorldMap;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import javax.swing.*;

public class RingBig extends Objects implements ActionAble {
    private boolean isAccepted = false;

    public RingBig(WorldMap map, Position position) {
        super(map, position.add(new Position(26,-2)), null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16 + 4,GameSettings.realHight/16/5));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        if(map.getBounce().getSize().getHight() == 64 && !isAccepted){
            isAccepted = true;
            map.subRing();
            setImage(new ImageIcon("Pictures/ringAcc.png").getImage());
        }
    }

}
