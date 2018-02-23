package ActionObjects;

import Game.WorldMap;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import javax.swing.*;
import java.awt.*;

public class CheckPoint extends Objects implements ActionAble {
    private boolean isChecked = false;

    public CheckPoint(WorldMap map, Position position) {
        super(map,position,null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        if(!isChecked) {
            map.setSpawnPoint(map.getBounce().getPosition(), map.getScreen().getPosition(),map.getMapLevel());
            isChecked = true;
        }
        setImage(new ImageIcon("Pictures/checked.png").getImage());
    }
}
