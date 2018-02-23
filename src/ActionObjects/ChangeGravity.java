package ActionObjects;

import Game.WorldMap;
import GameLogic.Velocity;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

public class ChangeGravity extends Objects implements ActionAble {
    public ChangeGravity(WorldMap map, Position position) {
        super(map, position, null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() {
        if(map.getBounce().getSize().getHight() != 64) return;
        map.getBounce().setInWater();
        if(GameSettings.gameGravity >= 0) {
            GameSettings.gameGravity = GameSettings.gravityInWater;
        }else if(map.getBounce().getVelocity().getDy() < 0 ){
            GameSettings.gameGravity = GameSettings.gravityInWaterWithForce;
        }
        if(map.getBounce().getPosition().getY() + map.getBounce().getSize().getHight()/2 < this.position.getY()
                && map.getBounce().getVelocity().getDy() < 0 && !map.getBounce().isInWater())
            GameSettings.gameGravity = GameSettings.gravityInAir;
        map.getBounce().setInWater();
    }
}
