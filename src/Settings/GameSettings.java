package Settings;

public class GameSettings {
    public static float normalHight = 1080;
    public static float normalWidth = 1920;
    public static float tableMapOneSize;
    public static float realHight;
    public static float realWidth;
    public static float maxAccelerationHorizontal = 0.01f; //0.009f
    public static float maxVelocityHorizontal = 0.7f; //2.5f
    public static float initialJumpVelocity = - 0.81f; //-3.1f
    public static float gameGravity = 0.001f; //0.007f
    public static float gravityInAir = 0.001f;
    public static float gravityInWater = -0.001f;
    public static float initialGravInWater = -0.001f;
    public static float gravityInWaterWithForce = gravityInWater/2;
    public static float minimalHorizontalVelocityNeededToBounceFromBrick = 0.2f;  //0.2f
    public static float onceUsedVelocityOnUpperCornersToMoveHorizontal = 0.2f; //0.2f
    public static float shortVersion = onceUsedVelocityOnUpperCornersToMoveHorizontal;
    public static int tableOfMapHightQuant = 16;  //ile jest w pionie kwadracikow

    public static void setSize(float hight,float width){
        System.out.println(hight + "   " + width);
        realHight = hight;
        realWidth = width;
        tableMapOneSize = realHight/16;
    }
}
