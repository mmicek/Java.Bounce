package Game;

import ActionObjects.*;
import GameObjects.*;
import Settings.GameSettings;
import Settings.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileLoader {
    private Scanner scanner;
    private WorldMap worldMap;
    List<Character> enemy = new ArrayList<>();
    List<Position> enemyPosition = new ArrayList<>();

    public FileLoader(int level,WorldMap worldMap) throws FileNotFoundException {
        scanner = new Scanner(new File("Levels/level" + level + ".txt"));
        this.worldMap = worldMap;
    }

    public void getMap(TableOfMap mapTable,int mapMaxLevel){
        for(int i=0;i<mapMaxLevel;i++){
            for(int y=0;y<16;y++){
                if(!scanner.hasNextLine()) return;
                String mapLine = scanner.nextLine();
                int x;
                for(x = 0;x<mapLine.length();x++){
                    addObjectOnMap(mapTable,mapLine.charAt(x),h(x),v(y),i);
                }
            }
        }
    }

    public int [] getRealLength(){
        String map = scanner.nextLine();
        int [] result = new int[quant];
        int j = 0;
        int size = quant;
        for(int i=0;i<size;i++){
            int number = 0;
            while(j < map.length() && map.charAt(j) != ' ') {
                number = number*10 + map.charAt(j) - 48;
                j++;
            }
            j++;
            result[i] = number;
        }
        return result;
    }

    public Position getBallPosition(){
        return new Position(toInt(scanner.nextLine()),toInt(scanner.nextLine()));
    }

    public void addObjectOnMap(TableOfMap map,char obj,float x,float y,int mapLevel){
        switch(obj){
            case ' ':
                break;
            case 'x':
                map.addObjectOnMap(new Brick(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'e':
                map.addObjectOnMap(new EnemyBrick(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'n':
                map.addObjectOnMap(new JumpBonus(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'b':
                map.addObjectOnMap(new BiggerBallBonus(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'w':
                map.addObjectOnMap(new ChangeGravity(worldMap,new Position(x,y)),mapLevel);
                break;
            case '+':
                map.addObjectOnMap(new NextLevelBlock(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'o':
                map.addObjectOnMap(new RingSmall(worldMap,new Position(x,y)),mapLevel);
                worldMap.addRing();
                break;
            case 'O':
                map.addObjectOnMap(new RingBig(worldMap,new Position(x,y)),mapLevel);
                worldMap.addRing();
                break;
            case '-':
                map.addObjectOnMap(new CancelBonus(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'c':
                map.addObjectOnMap(new CheckPoint(worldMap,new Position(x,y)),mapLevel);
                break;
            case 'E':
                map.addObjectOnMap(new EnemyBrickDown(worldMap,new Position(x,y)),mapLevel);
                break;
            default:
                if(enemy.contains(invert(obj))){
                    map.addEnemy(new Enemy(worldMap,new Position(x,y),new Position(x,y),enemyPosition.get(enemy.indexOf(invert(obj)))),mapLevel);
                    enemyPosition.remove(enemy.indexOf(invert(obj)));
                    enemy.remove(enemy.indexOf(invert(obj)));
                }else{
                   enemy.add(obj);
                   enemyPosition.add(new Position(x,y));
                }
                break;
        }
    }

    private int quant;
    public int[] getSettings(){
        int result[];
        int size = toInt(scanner.nextLine());
        result = new int[size + 2];
        result[0] = size;
        result[1] = toInt(scanner.nextLine());
        String map = scanner.nextLine();
        quant = size;
        int j = 0;
        for(int i=0;i<size;i++){
            int number = 0;
            while(j < map.length() && map.charAt(j) != ' ') {
                number = number*10 + map.charAt(j) - 48;
                j++;
            }
            j++;
            result[i+2] = number;
        }
        return result;
    }

    public int toInt(String s){
        int result = 0;
        for(int i=0;i<s.length();i++){
            result = result*10 + s.charAt(i) - 48;
        }
        return result;
    }

    public float v(int y){ //do gory
        if(y % 2 == 0)
            return (int)(y* GameSettings.tableMapOneSize);
        else
            return (int)(y*GameSettings.tableMapOneSize) + 1;
    }

    public float h(int x){ //na boki
        if(x % 2 == 0)
            return (int)(x*GameSettings.tableMapOneSize);
        else
            return (int)(x*GameSettings.tableMapOneSize) + 1;
    }

    public boolean isBigLetter(char c){
        return c > 64 && c < 91;
    }

    public char invert(char c){
        if(isBigLetter(c))
           return Character.toLowerCase(c);
        return Character.toUpperCase(c);
    }
}
