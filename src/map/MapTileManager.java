package map;

import static helpers.Constants.SpriteSheet.*;
import static helpers.Constants.MapTiles.*;

import helpers.LoadSave;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapTileManager {
    private BufferedImage spriteSheet;
    public ArrayList<MapTile> mapTiles = new ArrayList<>();
    public MapTile GRASS_TILE, FOREST_TILE, IRON_TILE, COPPER_TILE;

    public MapTileManager() {
        loadSpriteSheet();
        createMapTiles();
    }

    private void loadSpriteSheet() {
        spriteSheet = LoadSave.getSpriteSheet();
    }

    private void createMapTiles() {
        int id = 0;
        mapTiles.add(new MapTile(getSprite(GRASS_POS[0], GRASS_POS[1]), id++, GRASS));
        mapTiles.add(new MapTile(getSprite(FOREST_POS[0], FOREST_POS[1]), id++, FOREST));
        mapTiles.add(new MapTile(getSprite(IRON_POS[0], IRON_POS[1]), id++, IRON));
        mapTiles.add(new MapTile(getSprite(COPPER_POS[0], COPPER_POS[1]), id++, COPPER));
    }

    private BufferedImage getSprite(int x, int y) {
        return spriteSheet.getSubimage(x * 32, y * 32, 32, 32);
    }

    public BufferedImage getSprite(int id) {
        return mapTiles.get(id).getSprite();
    }

    public MapTile getMapTile(int id) {
        return mapTiles.get(id);
    }
}
