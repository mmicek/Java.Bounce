package Settings;

import GameLogic.Velocity;

public class Position {
    private final float x;
    private final float y;

    public Position(float x, float y){
        this.x = x;
        this.y = y;
    }

    public Position add(Position position){
        return new Position(this.getX()+position.getX(),this.getY()+position.getY());
    }

    public Position changePosition(Velocity velocity, long elapsedTime){
        return this.add(new Position(velocity.getDx()*elapsedTime,velocity.getDy()*elapsedTime));
    }

    public int getX(){
        return Math.round(x);
    }

    public int getY(){
        return Math.round(y);
    }

    @Override
    public String toString() {
        return x+"  "+y;
    }

    public float getFloatY(){
        return y;
    }

    public float getFloatX(){
        return x;
    }

    public boolean isBetween(Position from,Position to){
        return ((x >= from.getX() && x <= to.getX()) || (x <= from.getX() && x >= to.getX()))
                && ((y >= from.getY() && y <= to.getY()) || (y <= from.getY() && y >= to.getY()));
    }

    public boolean isCloser(Position isClose,Position thanThis){
        return (x-isClose.getX())*(x-isClose.getX()) + (y-isClose.getY())*(y-isClose.getY())
                < (x-thanThis.getX())*(x-thanThis.getX()) + (y-thanThis.getY())*(y-thanThis.getY());
    }
}
