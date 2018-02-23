package GameLogic;

public class Velocity {
    private final float dx;
    private final float dy;

    public Velocity(float dx, float dy){
        this.dx = dx;
        this.dy = dy;
    }

    public float getDx(){
        return dx;
    }

    public float getDy(){
        return dy;
    }

    public Velocity invert(){
        return new Velocity(-dx,-dy);
    }

    @Override
    public String toString() {
        return dx + "  " + dy;
    }
}

