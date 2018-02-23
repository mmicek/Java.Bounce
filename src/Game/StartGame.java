package Game;

import GameLogic.Screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class StartGame{
    private static int level = -1;
    public void start() throws InvocationTargetException, InterruptedException, IOException {
        int maxLevels = 0;
        int maxMaxLevels = 3;

        Scanner scanner = new Scanner(new File("Levels/unlocked.txt"));
        maxLevels = toInt(scanner.nextLine());
        if(maxLevels > maxMaxLevels)
            maxLevels = maxMaxLevels;

        MiniMenu menu = new MiniMenu(maxLevels);
        while(true) {
            System.out.println("!");
            if(level != -1) {
                Screen screen = new Screen();
                WorldMap map = new WorldMap(level);
                screen.setMap(map);
                map.setScreen(screen);
                map.run();
            }
        }
    }
    public static void setLevel(int i){
        level = i;
    }

    public static int toInt(String s){
        int result = 0;
        for(int i = 0;i<s.length();i++)
            result = result*10 + s.charAt(i) - 48;
        return result;
    }

    public static int getLevel(){
        return level;
    }
}
