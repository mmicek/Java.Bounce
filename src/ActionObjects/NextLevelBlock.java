package ActionObjects;

import Game.StartGame;
import Game.WorldMap;
import GameObjects.ActionAble;
import GameObjects.Objects;
import Settings.GameSettings;
import Settings.Position;
import Settings.Size;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class NextLevelBlock extends Objects implements ActionAble {
    public NextLevelBlock(WorldMap map, Position position) {
        super(map,position,null);
        setSize(new Size(Size.Shape.Rect, GameSettings.realHight/16,GameSettings.realHight/16));
        setImageIcon(getImageIcon(this));
    }

    @Override
    public void executeAction() throws FileNotFoundException {
        if(map.canGoToNextLevel()) {
            Scanner scanner = new Scanner(new File("Levels/unlocked.txt"));
            if(StartGame.toInt(scanner.nextLine()) == StartGame.getLevel()){
                scanner.close();
                File file = new File("Levels/unlocked.txt");
                PrintWriter writer = new PrintWriter(file);
                writer.write(StartGame.getLevel() + 49);
                writer.close();
            }
            map.setEnd();
        }
    }
}
