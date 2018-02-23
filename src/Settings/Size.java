package Settings;

public class Size {
    public enum Shape{
        Rect,Circle
    }

    private final float hight;
    private final float width;
    private final Shape shape;

    public Size(Shape shape, float hight, float width){
        this.shape = shape;
        this.hight = hight;
        this.width = width;
    }

    public Shape getShape() {
        return shape;
    }

    public int getHight() {
        return Math.round(hight);
    }

    public int getWidth() {
        return Math.round(width);
    }
}
