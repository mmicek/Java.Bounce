package Game;

import GameLogic.Screen;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Bounce {
    public static void main(String [] args) throws InvocationTargetException, InterruptedException, IOException {
        new StartGame().start();
    }
}
