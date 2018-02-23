package GameObjects;

import Settings.Position;
import Settings.Size;
import GameLogic.Velocity;
import Game.WorldMap;

public abstract class MobileObjects extends Objects {
    protected Velocity velocity;

    public MobileObjects(WorldMap map, Position position, Velocity velocity, Size size){
        super(map,position,size);
        this.velocity = velocity;
    }

    public void update(long elapsedTime,Velocity velocity){
        position = getPosition().changePosition(velocity, elapsedTime);
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity){
        this.velocity = velocity;
    }

}
