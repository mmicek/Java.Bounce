package GameObjects;

import Game.WorldMap;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

public class JumpBonus extends Objects implements ActionAble {
    public JumpBonus(WorldMap map, Position position) {
        super(map,position,null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        if(map.getBounce().isJumpButtonHolding()){
            map.getBounce().setJumpBoost(map.getBounce().getJumpBoost() + 0.2f);
        }
    }
}
