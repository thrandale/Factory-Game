package helpers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static helpers.Constants.SpriteSheet.COIN_POS;

import javax.imageio.ImageIO;

import helpers.Constants.GameConstants;

public class LoadSave {
    public static BufferedImage getSpriteSheet() {
        BufferedImage spriteSheet = null;
        try {
            spriteSheet = ImageIO.read(LoadSave.class.getResource("/sprites/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return spriteSheet;
    }

    public static BufferedImage getStdCursor() {
        BufferedImage cursor = null;
        try {
            cursor = ImageIO.read(LoadSave.class.getResource("/sprites/cursors.png")).getSubimage(0, 0, 22, 22);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public static BufferedImage getMoveCursor() {
        BufferedImage cursor = null;
        try {
            cursor = ImageIO.read(LoadSave.class.getResource("/sprites/cursors.png")).getSubimage(22, 0, 22, 22);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public static BufferedImage getCoin() {
        return getSpriteSheet().getSubimage(COIN_POS[0] * 32, COIN_POS[1] * 32, 32, 32);
    }

    public static void saveMap(int[][] map, String fileName) {
        File mapFile = new File("res/save/" + fileName + ".txt");
        // create the map file if it doesn't exist
        if (!mapFile.exists()) {
            try {
                mapFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // save the map
        try {
            PrintWriter writer = new PrintWriter(mapFile);
            int arr[] = twoD2oneD(map);
            for (Integer i : arr) {
                writer.println(i);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static int[][] loadMap(String fileName) {
        File mapFile = new File("res/save/" + fileName + ".txt");
        ArrayList<Integer> mapList = new ArrayList<Integer>();

        if (!mapFile.exists()) {
            System.out.println("File " + fileName + ".txt not found!");
            System.exit(0);
            return null;
        }

        try {
            Scanner reader = new Scanner(mapFile);
            while (reader.hasNextLine()) {
                mapList.add(Integer.parseInt(reader.nextLine()));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return list2Array(mapList, GameConstants.MAP_WIDTH / 32, GameConstants.MAP_HEIGHT / 32);
    }

    private static int[][] list2Array(ArrayList<Integer> list, int xSize, int ySize) {
        int newArr[][] = new int[xSize][ySize];

        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                newArr[i][j] = list.get(i * ySize + j);
            }
        }

        return newArr;
    }

    private static int[] twoD2oneD(int[][] twoArr) {
        int[] oneArr = new int[twoArr.length * twoArr[0].length];

        for (int i = 0; i < twoArr.length; i++) {
            for (int j = 0; j < twoArr[0].length; j++) {
                oneArr[i * twoArr[0].length + j] = twoArr[i][j];
            }
        }

        return oneArr;
    }
}
