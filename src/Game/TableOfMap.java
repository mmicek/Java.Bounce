package Game;

import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TableOfMap {
    /**
     * UWAGA WYSKOSM MAPY TO 8
     */

    private int mapLevel;  //ktore poziom mapy tj ona podzielona na takie paski kilkupoziomowe
    private int maxMapLevel;
    private WorldMap worldMap;
    private Objects map [][][];
    private ArrayList<ArrayList<Enemy>> enemyList;
    private int realWidth [];

    TableOfMap(int maxMapLevel,WorldMap worldMap){
        this.maxMapLevel = maxMapLevel;
        realWidth = new int[maxMapLevel];
        this.worldMap = worldMap;
        map = new Objects[maxMapLevel][][];
        enemyList = new ArrayList<ArrayList<Enemy>>();
        for(int i=0;i<maxMapLevel;i++)
            enemyList.add(new ArrayList<>());
    }

    public void setMapLength(int mapLevel,int length){
        map[mapLevel] = new Objects[length][GameSettings.tableOfMapHightQuant];
    }

    public void setRealWidth(int mapLevel,int realLength){
        realWidth[mapLevel] = realLength;
    }

    public void addObjectOnMap(Objects objects,int mapLevel){
            map[mapLevel][Math.round(objects.getPosition().getX()/GameSettings.tableMapOneSize)][Math.round(objects.getPosition().getY()/GameSettings.tableMapOneSize)] = objects;
    }

    public void setMapLevel(int level){
        this.mapLevel = level;
    }

    public int getMapLength(){
        return map[mapLevel].length;
    }

    public Objects getObjectAt(Position position){
        if(position.getX() < map[mapLevel].length && position.getY() < GameSettings.tableOfMapHightQuant && position.getX() >= 0 && position.getY() >= 0) //zmianiaa rozmiaru ilosci kloskcoq
            return map[mapLevel][(int)Math.floor(position.getX()/GameSettings.tableMapOneSize)][(int)Math.floor(position.getY()/GameSettings.tableMapOneSize)];
        return null;
    }

    public Objects getObjectAt(int x,int y){
        if(x >= 0 && x < map[mapLevel].length && y >= 0 && y < GameSettings.tableOfMapHightQuant)   //tu zmienic jak rozmiar may sie zmienia
            return map[mapLevel][x][y];
        return null;
    }

    public ArrayList<Objects> getObjectsToPaint(int startingPoint){
        ArrayList <Objects> objectsList = new ArrayList<>();
        int endingPoint = (int)Math.floor(startingPoint/GameSettings.tableMapOneSize) + (int)Math.floor(GameSettings.realWidth/GameSettings.tableMapOneSize)+1;
        for(int i = (int)Math.floor(startingPoint/GameSettings.tableMapOneSize);i<=endingPoint;i++)
            for(int j = 0;j<GameSettings.tableOfMapHightQuant;j++) {
                Objects objects = getObjectAt(i,j);
                if(objects != null) objectsList.add(objects);
            }
        for (Enemy e:getActualEnemyOnMap()
             ) {
            objectsList.add(e);
        }
        return objectsList;
    }

    public void addEnemy(Enemy enemy,int mapLevel){
        enemyList.get(mapLevel).add(enemy);
    }

    public ArrayList<Enemy> getActualEnemyOnMap() {
        return enemyList.get(mapLevel);
    }

    public int getMaxMapLevel(){
        return maxMapLevel;
    }

    public boolean canChangeStripe(int mapLevel){
        return mapLevel >= 0 && mapLevel < maxMapLevel;
    }

    public int getMapLevel(){
        return  mapLevel;
    }

    public int getMapRealLength(){
        return realWidth[mapLevel];
    }
}

