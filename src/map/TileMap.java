package map;

import static helpers.Constants.GameConstants.*;

import helpers.LoadSave;

import java.awt.Graphics;

public class TileMap {

    private MapTileManager mapTileManager;

    private int[][] map = new int[MAP_WIDTH / 32][MAP_HEIGHT / 32];
    private int globalX = -32 * 8, globalY = -32 * 8;
    private float mapScale = 1;
    private int tileSize = 32;
    private int prevSize = 0;

    public TileMap() {
        init();
    }

    private void init() {
        mapTileManager = new MapTileManager();
        map = LoadSave.loadMap("map");
    }

    public void render(Graphics g) {
        for (int x = 0; x < MAP_WIDTH / 32; x++) {
            for (int y = 0; y < MAP_HEIGHT / 32; y++) {
                int imgX = (int) (x * 32 * mapScale) + globalX;
                int imgY = (int) (y * 32 * mapScale) + globalY;
                g.drawImage(mapTileManager.getSprite(map[x][y]), imgX, imgY, tileSize, tileSize, null);
            }
        }
    }

    public void pan(int x, int y, int lastX, int lastY) {
        int deltaX = x - lastX;
        int deltaY = y - lastY;
        globalX += deltaX;
        globalY += deltaY;

        constrainMap();
    }

    public void scale(int rotation, int mouseX, int mouseY) {
        prevSize = tileSize;

        // scale the map
        if (rotation > 0) {
            mapScale -= GAME_SCALE_SPEED;
        } else {
            mapScale += GAME_SCALE_SPEED;
        }

        // constrain map scale
        if (mapScale < MIN_SCALE) {
            mapScale = MIN_SCALE;
        }
        if (mapScale > MAX_SCALE) {
            mapScale = MAX_SCALE;
        }

        // set the new tile size
        tileSize = (int) (32 * mapScale);

        // center the zoom on the mouse
        if (rotation > 0) {
            globalX += ((prevSize - tileSize) * mouseX);
            globalY += ((prevSize - tileSize) * mouseY);
        } else {
            globalX -= ((tileSize - prevSize) * mouseX);
            globalY -= ((tileSize - prevSize) * mouseY);
        }

        constrainMap();
    }

    private void constrainMap() {
        if (globalX > 0) {
            globalX = 0;
        }
        if (globalY > 0) {
            globalY = 0;
        }
        if (globalX + MAP_WIDTH * mapScale < GAME_WIDTH) {
            globalX = (int) (GAME_WIDTH - MAP_WIDTH * mapScale);
        }
        if (globalY + MAP_HEIGHT * mapScale < GAME_HEIGHT) {
            globalY = (int) (GAME_HEIGHT - MAP_HEIGHT * mapScale);
        }
    }

    public void save() {
        LoadSave.saveMap(map, "map");
    }

    public MapTile getTile(int index) {
        return mapTileManager.getMapTile(index);
    }

    public void setTile(int x, int y, int tile) {
        map[x][y] = tile;
    }

    public void setSize(int size) {
        tileSize = size;
    }

    public int getSize() {
        return tileSize;
    }

    public int getGlobalX() {
        return globalX;
    }

    public int getGlobalY() {
        return globalY;
    }

}
