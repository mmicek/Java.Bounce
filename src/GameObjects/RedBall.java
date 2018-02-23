package GameObjects;

import Game.*;
import GameLogic.Collision;
import GameLogic.Velocity;
import Settings.Direction;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class RedBall extends MobileObjects {
    private float maxVelocity = 0;
    private float accelerationX = 0;
    private Direction helpDirection = null;
    private Position groundPosition = new Position(0,0);
    private int lives = 3;
    private boolean jumpButtonHolding = false;
    private float jumpBoost = 1;

    public RedBall(WorldMap map, Position position, Velocity velocity, Size size){
        super(map,position,velocity,size);
        setImageIcon(getImageIcon(this));
    }

    private float Dx;
    private float Dy;
    public void updateWithForce(long elapsedTime) throws IOException {
        checkToChangeStripe();
        Dx = updateHorizonatally(elapsedTime);
        Dy = updateVertically(elapsedTime);
        setVelocity(new Velocity(Dx,Dy));
        //checkToCancelBonus();
    }

    public float lastVelocityValue = 0;
    public float updateVertically(long elapsedTime) throws IOException {
        float Dy;
        Dy = getVelocity().getDy();
        if(Dy != 0) lastVelocityValue = Dy;
        Dy += GameSettings.gameGravity*elapsedTime;

        Position oldPosition = position;
        super.update(elapsedTime,new Velocity(0,Dy));
        Collision collisionType = colisionTestForBounce(0);  //dla wszystkich objektow
        if(!inWater) {
            GameSettings.gameGravity = GameSettings.gravityInAir;
        }
        if(collisionType == Collision.Up || collisionType == Collision.LeftUpperCorner || collisionType == Collision.RightUpperCorner){
            groundPosition = oldPosition;
            position = oldPosition;
            Dy = 0;
            if(lastVelocityValue > GameSettings.minimalHorizontalVelocityNeededToBounceFromBrick) {
                Dy = -lastVelocityValue / 2;
            }
            if(jumpButtonHolding)
                Dy = jump();
        }
        else if(collisionType != Collision.NoCollision && collisionType != Collision.Invincible) {
            position = oldPosition;
            Dy = 0;
        }

        if(toSetBigger){
            adjustPosition();
            toSetBigger = false;
        }
        return Dy;
    }

    public float updateHorizonatally(long elapsedTime) throws IOException {
        float Dx = getVelocity().getDx();
        if (maxVelocity == 0) {
            Dx += elapsedTime * accelerationX;
            if ((Dx < 0 && accelerationX <= 0) || (Dx > 0 && accelerationX >= 0)) {
                Dx = 0;
                accelerationX = 0;
            }
        } else if (abs(Dx + elapsedTime * accelerationX) < abs(maxVelocity))
            Dx += elapsedTime * accelerationX;

        Position oldPosition = position;
        super.update(elapsedTime,new Velocity(Dx,0));
        Collision collisionType = colisionTestForBounce(1);   //dla wszystkich objektow
        if(collisionType == Collision.LeftUpperCorner || collisionType == Collision.RightUpperCorner) {
            position = oldPosition;
            updateOnCorner(elapsedTime);
            Dx = 0;

        }else if(collisionType != Collision.NoCollision && collisionType != Collision.Invincible){
            position = oldPosition;
            Dx = Dx/3;
            if(Dx < 0.001f)
                Dx = 0;
        }
        if(position != oldPosition){
            if(Math.floor(oldPosition.getX()) < Math.floor(position.getX()) && screenPosition().getX() > 2*GameSettings.realWidth/3
                    && position.getX() + 2*GameSettings.realWidth/3 < map.getRealMapLength()*GameSettings.realHight/16) {
                map.getScreen().setScreen(- (int) Math.floor(2*GameSettings.realWidth/3 - position.getX()));
            }
            else if(oldPosition.getX() > position.getX() && screenPosition().getX() < GameSettings.realWidth/5)
                map.getScreen().setScreen((int) Math.floor(-GameSettings.realWidth/5 + position.getX()));
        }

        if(toSetBigger){
            adjustPosition();
            toSetBigger = false;
        }
        return Dx;
    }

    public void updateOnCorner(long elapsedTime) throws IOException {
        Position testingPosition = position;
        if(helpDirection == Direction.Right)
            position = position.add(new Position(elapsedTime*(GameSettings.shortVersion),0));
        if(helpDirection == Direction.Left)
            position = position.add(new Position(elapsedTime*(-GameSettings.shortVersion),0));
        if(position.getX() != testingPosition.getX())
            while(colisionTestForBounce(1) != Collision.NoCollision )    //dla wszystich objektow
                position = new Position(position.getX(), position.getFloatY() - 0.5f);
    }

    public void startMoving(Direction direction){
        maxVelocity = GameSettings.maxVelocityHorizontal;
        if(direction == Direction.Right) {
            accelerationX = GameSettings.maxAccelerationHorizontal;
            helpDirection = Direction.Right;
        }
        else {
            helpDirection = Direction.Left;
            accelerationX = -GameSettings.maxAccelerationHorizontal;
        }
    }

    private Objects lastCollideObject = null;
    private Collision lastCollision;
    private boolean inWater;
    public Collision colisionTestForBounce(int orientation) throws IOException {
        inWater = false;
        for(int i=(int)Math.floor(position.getFloatX()/GameSettings.tableMapOneSize)-1;i<=(int)Math.floor((position.getFloatX()+size.getWidth())/GameSettings.tableMapOneSize)+1;i++)
            for(int j=(int)Math.floor(position.getFloatY()/GameSettings.tableMapOneSize)-1;j<=(int)Math.floor((position.getFloatY()+size.getHight())/GameSettings.tableMapOneSize)+1;j++) {
                if (map.getObjectAt(i,j) != null && map.getObjectAt(i,j).isCollision(this,orientation) != Collision.NoCollision) {
                    if(map.getObjectAt(i,j) instanceof ActionAble){
                        lastCollision = map.getObjectAt(i,j).isCollision(this,orientation);
                        ((ActionAble) map.getObjectAt(i,j)).executeAction();
                    }
                    if(map.getObjectAt(i,j).isCollision(this,orientation) != Collision.Invincible)
                        return map.getObjectAt(i,j).isCollision(this,orientation);
                }
            }
        ArrayList<Enemy> enemys = map.getEnemys();
        for (Enemy e:enemys) {
            if(e.isCollision(this,0) != Collision.NoCollision){
                e.executeAction();
            }
        }
        return Collision.NoCollision;
    }

    public boolean isInWater(){
        return inWater;
    }

    public void stopMoving(){
        maxVelocity = 0;
        accelerationX = -accelerationX;
        helpDirection = null;
    }

    public void setJump(){
        jumpButtonHolding = true;
    }

    public void stopJump(){
        jumpButtonHolding = false;
    }

    public float jump(){
        return GameSettings.initialJumpVelocity*jumpBoost;
    }

    private float abs(float x){
        if(x < 0) return -x;
        return x;
    }

    public Position screenPosition(){
        return new Position(position.getX() - map.getScreen().getPosition().getX(),position.getY());
    }

    public boolean isDead(){
        return lives <= 0;
    }

    public void changeLives(int i){
        lives = lives + i;
    }

    private boolean toSetBigger = false;
    public void setBigger(){
        position.add(new Position(0,-15));
        timeToCancelBonus = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        if(getSize().getHight() != 64) {
            toSetBigger = true;
            setSize(new Size(Size.Shape.Circle, 64, 64));
        }
    }

    private void adjustPosition(){
        switch(lastCollision){
            case Right:
                position = position.add(new Position(0,-15));
                break;
            case Left:
                position = position.add(new Position(-15,-15));
                break;
            case Up:
                position = position.add(new Position(0,-15));
                break;
            case LeftUpperCorner:
                position = position.add(new Position(-15,-15));
                break;
            case RightUpperCorner:
                position = position.add(new Position(0,-15));
                break;
            default:
                position = position.add(new Position(0,15));
        }
    }

    private long timeToCancelBonus = 0;
    public void cancelBonus(){
        //if(timeToCancelBonus == 0) return;
        //if(timeToCancelBonus < System.nanoTime()) {
            setSize(new Size(Size.Shape.Circle, 50, 50));
            //timeToCancelBonus = 0;
    }

    public void checkToChangeStripe(){
        if(position.getY() < 0) {
            map.canChangeStripe(map.getMapLevel() - 1);
            position = new Position(position.getX(),GameSettings.realHight);
        }
        else if(position.getY() > GameSettings.realHight){
            map.canChangeStripe(map.getMapLevel() + 1);
            position = new Position(position.getX(),0);
        }
    }

    public void setInWater(){
        inWater = true;
    }

    public void addLastCollideObject(Objects object){
        this.lastCollideObject = object;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public boolean isJumpButtonHolding(){
        return jumpButtonHolding;
    }

    public void setJumpBoost(float x){
        this.jumpBoost = x;
    }

    public float getJumpBoost(){
        return jumpBoost;
    }

    public boolean isBig(){
        return getSize().getHight() == 64;
    }
}
