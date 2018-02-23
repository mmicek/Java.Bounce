package GameObjects;

import ActionObjects.*;
import Game.Enemy;
import GameLogic.Collision;
import Settings.Position;
import Settings.Size;
import Game.WorldMap;

import javax.swing.*;
import java.awt.*;

public abstract class Objects {
    protected Position position;
    protected WorldMap map;
    private Image image;
    protected Size size;

    public Objects(WorldMap map, Position position, Size size){
        this.size = size;
        this.position = position;
        this.map = map;
    }

    public ImageIcon getImageIcon(Object object){
        if(object instanceof RedBall)
            return new ImageIcon("Pictures/bounce.png");
        if(object instanceof Brick)
            return new ImageIcon("Pictures/brick.jpg");
        if(object instanceof EnemyBrickDown)
            return new ImageIcon("Pictures/lord.png");
        if(object instanceof EnemyBrick)
            return new ImageIcon("Pictures/lorneta.png");
        if(object instanceof BiggerBallBonus){
            return new ImageIcon("Pictures/bonus.png");
        }
        if(object instanceof RingBig || object instanceof RingSmall){
            return new ImageIcon("Pictures/ring.png");
        }
        if(object instanceof ChangeGravity)
            return new ImageIcon("Pictures/grav.jpg");
        if(object instanceof NextLevelBlock)
            return new ImageIcon("Pictures/level.jpg");
        if(object instanceof Enemy){
            return new ImageIcon("Pictures/enemy.png");
        }
        if(object instanceof CheckPoint)
            return new ImageIcon("Pictures/check.png");
        if(object instanceof JumpBonus)
            return new ImageIcon("Pictures/jump.jpg");
        if(object instanceof CancelBonus)
            return new ImageIcon("Pictures/cancel.png");

        throw new IllegalArgumentException("Nie ma grafiki dla takiego objektu");
    }

    public Collision isCollision(Objects objects,int orientation){
        /**
         *  Wyszukuje kolizje domyslnie objektem jest Bounce
         *  Czyli trzeba wywolac metode dla kafelka
          */
        //Kolizja objektu kola z prostokatem

        if(this instanceof CheckPoint && orientation != -2 && isCollision(objects,-2) != Collision.NoCollision)
            return Collision.Invincible;
        if(objects instanceof RedBall)
            ((RedBall) objects).addLastCollideObject(this);
        if((this instanceof RingBig || this instanceof RingSmall) && orientation != 0 && ringCollision(objects) != null)
            return ringCollision(objects);
        if(this instanceof ChangeGravity && orientation != -1 && isCollision(objects,-1) != Collision.NoCollision)
            return Collision.Invincible;
        if(objects.getSize().getShape() == Size.Shape.Circle){
            Position center = new Position(objects.getPosition().getX() + objects.getSize().getWidth()/2,objects.getPosition().getY() + objects.getSize().getHight()/2);
            if(arePointsInCircle(objects,center) != Collision.NoCollision) return arePointsInCircle(objects,center);
            if(between(objects.position.getX() + objects.size.getWidth(),position.getX(),position.getX()+size.getWidth())   //lewy bok
                    && between(center.getY(),position.getY(),position.getY()+size.getHight(),true))  return Collision.Left;
            if(between(objects.position.getY()+objects.size.getHight(),position.getY(),position.getY()+size.getHight())   //gora
                    && between(center.getX(),position.getX(),position.getX()+size.getWidth(),true)) return Collision.Up;
            if(between(objects.position.getX(),position.getX(),position.getX()+size.getWidth())   //prawy bok
                    && between(center.getY(),position.getY(),position.getY()+size.getHight(),true)) return Collision.Right;
            if(between(objects.position.getY(),position.getY(),position.getY()+size.getHight())   //dol
                    && between(center.getX(),position.getX(),position.getX()+size.getWidth(),true)) return Collision.Down;
        }
        return Collision.NoCollision;
    }

    public boolean between(int pointBetween,int leftPoint,int rightPoint){
        return pointBetween > leftPoint && pointBetween < rightPoint || pointBetween < leftPoint && pointBetween > rightPoint;
    }

    public boolean between(int pointBetween,int leftPoint,int rightPoint,boolean andSmaller){
        return pointBetween >= leftPoint && pointBetween <= rightPoint || pointBetween <= leftPoint && pointBetween >= rightPoint;
    }

    public Collision arePointsInCircle(Objects objects,Position cener){
        /**
         * Sprawdza czy jakis z 4 rogow prostakata jest w kole
         * Jak tak to jest kolizja
         */
        Position center = new Position(objects.getPosition().getX() + objects.getSize().getWidth()/2,objects.getPosition().getY() + objects.getSize().getHight()/2);
        float r = objects.getSize().getHight()/2;
        if(distanceBeetwenTwoPoints(getPosition(),center) < r && getPosition().getY() > cener.getY()) return Collision.LeftUpperCorner;
        if(distanceBeetwenTwoPoints(new Position(getPosition().getX() + getSize().getWidth(),getPosition().getY()),center) < r && getPosition().getY() > cener.getY()) return Collision.RightUpperCorner;
        if(distanceBeetwenTwoPoints(new Position(getPosition().getX(),getPosition().getY()+getSize().getHight()),center)  < r) return Collision.DownCorner;
        if(distanceBeetwenTwoPoints(new Position(getPosition().getX()+getSize().getWidth(),getPosition().getY()+getSize().getHight()),center)  < r) return Collision.DownCorner;
        return Collision.NoCollision;
    }

    public Collision ringCollision(Objects objects){
        if(!(objects instanceof RedBall)) return null;
        if(objects.getSize().getHight() == 64 && this.getSize().getHight() > 64){
            if(objects.getPosition().getY() > this.getPosition().getY() + 4
                    && objects.getPosition().getY() + objects.getSize().getHight() < this.getPosition().getY() + this.getSize().getHight()
                    && isCollision(objects,0) != Collision.NoCollision)
                return Collision.Invincible;
        }else if(objects.getSize().getHight() != 64){
            if(objects.getPosition().getY() > this.getPosition().getY() + 4
                    && objects.getPosition().getY() + objects.getSize().getHight() < this.getPosition().getY() + this.getSize().getHight()
                    && isCollision(objects,0) != Collision.NoCollision)
                return Collision.Invincible;
        }
        return null;
    }

    public float distanceBeetwenTwoPoints(Position position,Position center){
        return (float)(Math.sqrt(sqr(position.getX() - center.getX()) + sqr(position.getY() - center.getY())));
    }

    public float sqr(int i){
        return i*i;
    }

    public void setImageIcon(ImageIcon icon){
        image = icon.getImage();
    }

    public Position getPosition() {
        return position;
    }

    public Image getImage() {
        if(image == null) throw new IllegalArgumentException("Brak grafiki dla objektu. Prawdopodobnie bledny konstruktor");
        return image;
    }

    public Size getSize(){
        return size;
    }

    public void setSize(Size size){
        this.size = size;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public void setImage(Image image){
        this.image = image;
    }
}
