package Game;
import GameLogic.Screen;
   import GameLogic.Velocity;
import GameObjects.*;
import Settings.GameSettings;
   import Settings.Position;
   import Settings.Size;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class WorldMap {
    private long time;
    private Screen screen;
    private TableOfMap mapTable;
    private RedBall bounce;
    private Position spawnPosition;
    private Position screenSpawnPosition = new Position(0,0);
    private int mapLevelSpawn;
    private int ringsToEnd = 0;
    private boolean end = false;

    private int level;
    WorldMap(int level){
        this.level = level;
    }

    public void run() throws IOException {
        /**
         *  Glowna petla sterowanie gra to jest
         */
        GameSettings.setSize(screen.getHeight(),screen.getWidth());
        loadMap();

        time = System.currentTimeMillis();
        while(!end) {
                long elapsedTime = System.currentTimeMillis() - time;
                time += elapsedTime;

                bounce.updateWithForce(elapsedTime);
                for (Enemy e : getEnemys()) {
                    e.update(elapsedTime);
                }
            screen.draw();
        }
        screen.quitScreen();
    }

    public void setEnd(){
        end = true;
    }

    public void setScreen(Screen screen){
        this.screen = screen;
    }

    public Screen getScreen(){
        return screen;
    }

    public RedBall getBounce() {
        return bounce;
    }

    public void loadMap() throws IOException {

        FileLoader loader;
        try {
            loader = new FileLoader(this.level,this);
            spawnPosition = loader.getBallPosition();
            bounce = new RedBall(this,spawnPosition,new Velocity(0,0),new Size(Size.Shape.Circle,50,50));
            int [] settings = loader.getSettings();
            mapTable = new TableOfMap(settings[0],this);
            mapLevelSpawn = settings[1];
            mapTable.setMapLevel(settings[1]);
            for(int i = 2;i<settings.length;i++) {
                mapTable.setMapLength(i - 2, settings[i]);
            }
            int [] length = loader.getRealLength();
            for(int i = 0;i<length.length;i++) {
                mapTable.setRealWidth(i, length[i]);
            }
            loader.getMap(mapTable,mapTable.getMaxMapLevel());

        }catch(FileNotFoundException e){
            System.out.println("Nie znaleziono okreslonego levelu - pliku");
            screen.quitScreen();
        }

        if(bounce.getPosition().getX() > GameSettings.realWidth/2){
            screen.setScreen((int)(bounce.getPosition().getX() - GameSettings.realWidth/2));
            screenSpawnPosition = screen.getPosition();
        }

//        mapTable = new TableOfMap(1,this);
//        mapTable.setMapLevel(0);
//        mapTable.setMapLength(0,2500);
//        bounce = new RedBall(this,new Position(140f,0f),new Velocity(0,0),new Size(Size.Shape.Circle,50,50)); //50,50
//
//
//        for(int h = 0;h<29;h++)
//            mapTable.addObjectOnMap(new Brick(this,new Position(h(h),v(11))),0);
//        mapTable.addObjectOnMap(new EnemyBrick(this,new Position(h(3),v(10))),0);
//        mapTable.addObjectOnMap(new BiggerBallBonus(this,new Position(h(6),v((10)))),0);
//        mapTable.addObjectOnMap(new RingBig(this,new Position(h(8),v(10))),0);
//        mapTable.addObjectOnMap(new RingSmall(this,new Position(h(10),v(10))),0);
//
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(11),v(10))),0);
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(12),v(10))),0);
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(11),v(9))),0);
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(12),v(9))),0);
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(11),v(8))),0);
//        mapTable.addObjectOnMap(new ChangeGravity(this,new Position(h(12),v(8))),0);
//
//        mapTable.addObjectOnMap(new Brick(this,new Position(h(13),v(9))),0);
//        mapTable.addObjectOnMap(new NextLevelBlock(this,new Position(h(14),v(9))),0);
//        mapTable.addObjectOnMap(new Brick(this,new Position(h(19),v(9))),0);
//
//        mapTable.addObjectOnMap(new JumpBonus(this,new Position(h(20),v(10))),0);
//        mapTable.addObjectOnMap(new JumpBonus(this,new Position(h(21),v(10))),0);
//
//        mapTable.addEnemy(new Enemy(this,new Position(h(9),v(9)),new Position(h(9),v(9)),new Position(h(12),v(5))),0);
//        mapTable.addEnemy(new Enemy(this,new Position(h(36),v(9)),new Position(h(36),v(9)),new Position(h(36),v(12))),0);
    }

    public float v(int x){ //do gory
        if(x % 2 == 0)
            return (int)(x*GameSettings.tableMapOneSize);
        else
            return (int)(x*GameSettings.tableMapOneSize) + 1;
    }

    public float h(int x){ //na boki
        if(x % 2 == 0)
            return (int)(x*GameSettings.tableMapOneSize);
        else
            return (int)(x*GameSettings.tableMapOneSize) + 1;
    }

    public void addRing(){
        ringsToEnd++;
    }

    public void subRing(){
        ringsToEnd--;
    }

    public boolean canGoToNextLevel(){
        return ringsToEnd == 0;
    }

    public Objects getObjectAt(Position position){
        return mapTable.getObjectAt(position);
    }

    public Objects getObjectAt(int x,int y){
        return mapTable.getObjectAt(x,y);
    }

    public ArrayList<Objects> getObjectsToPaint(){
        return mapTable.getObjectsToPaint(screen.getPosition().getX());
    }

    public int getMapLength(){
        return mapTable.getMapLength();
    }

    public void setSpawnPosition(Position spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    public Position getSpawnPosition() {
        return spawnPosition;
    }

    public TableOfMap getMapTable() {
        return mapTable;
    }

    public ArrayList<Enemy> getEnemys(){
        return mapTable.getActualEnemyOnMap();
    }

    public void setSpawnPoint(Position spawnPosition,Position screenSpawnPosition,int level){
        this.spawnPosition = spawnPosition;
        this.screenSpawnPosition = screenSpawnPosition;
        this.mapLevelSpawn = level;
    }

    public Position getScreenSpawnPosition() {
        return screenSpawnPosition;
    }

    public void canChangeStripe(int mapLevel){
        if(mapTable.canChangeStripe(mapLevel))
            mapTable.setMapLevel(mapLevel);
    }

    public int getMapLevel(){
        return mapTable.getMapLevel();
    }

    public int getRealMapLength(){
        return mapTable.getMapRealLength();
    }

    public void setMapLevel(){
        mapTable.setMapLevel(mapLevelSpawn);
    }
}
