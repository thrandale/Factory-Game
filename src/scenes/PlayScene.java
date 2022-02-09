package scenes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import static helpers.Constants.Colors.*;
import static helpers.Constants.GameConstants.*;

import helpers.Constants;
import helpers.LoadSave;
import main.Game;
import map.MapTile;
import map.TileMap;
import objects.Button;
import structures.Structure;
import structures.StructureManager;

public class PlayScene implements SceneMethods {
    private Game game;
    private Graphics2D g2d;
    private ShopScene shopScene;
    private Button shopButton;
    private TileMap map;
    private StructureManager structureManager;

    private boolean isInShop;

    private int lastMouseX = 0, lastMouseY = 0;
    private int globalMouseX, globalMouseY;
    private int localMouseX, localMouseY;
    private boolean rc = false;
    private int currentGold = STARTING_GOLD;
    private Structure selectedStructure;
    private Structure selectedShopStructure;
    int[][] availablePositions = new int[3][2];

    private boolean edit = false;
    private MapTile selectedTile = null;

    public PlayScene(Game game) {
        this.game = game;
        init();
    }

    private void init() {
        map = new TileMap();
        structureManager = new StructureManager(this);
        selectedTile = map.getTile(1);
        shopScene = new ShopScene(this);
        shopButton = new Button(GAME_WIDTH - 10 - 30, GAME_HEIGHT - 10 - 30, 30, 30);

        for (int i = 0; i < availablePositions.length; i++) {
            for (int j = 0; j < availablePositions[i].length; j++) {
                availablePositions[i][j] = -2;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g2d = (Graphics2D) g;

        // background
        g2d.setColor(new Color(60, 112, 71));
        g2d.setStroke(new BasicStroke(2));
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

        map.render(g);
        structureManager.render(g);
        drawSelectedShopTile(g);

        if (shopScene.isOpen()) {
            shopScene.render(g);
        }

        drawSelectedBelt(g);

        drawGold(g);
        shopButton.render(g);
    }

    @Override
    public void update() {
        structureManager.update();
    }

    private void drawSelectedBelt(Graphics g) {
        if (selectedStructure == null) {
            return;
        }
        if (selectedStructure.getType() == Constants.BeltTypes.BELT_STRAIGHT) {
            // get the available positions
            availablePositions = structureManager.getAvailablePositions(selectedStructure);

            for (int i = 0; i < availablePositions.length; i++) {
                if (availablePositions[i][0] == -2) {
                    continue;
                }

                g2d.setColor(VALID_COLOR);
                g2d.fillRect((selectedStructure.getX() + availablePositions[i][0]) * map.getSize() + map.getGlobalX(),
                        (selectedStructure.getY() + availablePositions[i][1]) * map.getSize() + map.getGlobalY(),
                        map.getSize(), map.getSize());
            }

        }
    }

    private void drawSelectedShopTile(Graphics g) {
        if (!isInShop && selectedShopStructure != null) {
            g.setColor(VALID_COLOR);
            g.fillRect(localMouseX, localMouseY, map.getSize(), map.getSize());
            g.drawImage(structureManager.getBeltSprite(selectedShopStructure.getRotation()), localMouseX, localMouseY,
                    map.getSize(), map.getSize(), null);
        }
    }

    private void drawBackgroundBox(Graphics g, int x, int y, int width, int height) {
        int cornerRadius = (int) Math.min((Math.min(width, height) * .5), 30);
        g.setColor(BUTTON_COLOR);
        g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);
    }

    public void drawGold(Graphics g) {
        int x = 10;
        int y = 10;
        int width = 75;
        int height = 30;
        String gold;
        int goldSize = 16;
        drawBackgroundBox(g, x, y, width, height);

        if (currentGold < 10000) {
            gold = String.valueOf(currentGold);
        } else {
            gold = String.valueOf(currentGold / 1000) + "k";
        }

        g2d.setFont(new Font(FONT, Font.BOLD, 20));
        g2d.setColor(Color.WHITE);
        g2d.drawString(gold, x + width - g2d.getFontMetrics().stringWidth(gold) - 5, y + 22);
        g2d.drawImage(LoadSave.getCoin(), x + 5, y + height / 2 - goldSize / 2, goldSize, goldSize, null);
    }

    private void setMouseLocations(int x, int y) {
        // global mouse location is the tile index from the left of the map
        globalMouseX = ((x - map.getGlobalX()) / map.getSize() * map.getSize()) / map.getSize();
        globalMouseY = ((y - map.getGlobalY()) / map.getSize() * map.getSize()) / map.getSize();

        // local mouse location is the tile location from the screen
        localMouseX = ((globalMouseX * map.getSize()) + map.getGlobalX());
        localMouseY = ((globalMouseY * map.getSize()) + map.getGlobalY());
    }

    public void onClose() {
        System.out.println("Saving game...");
        map.save();
        structureManager.saveMap();
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public void setSelectStructure(Structure s) {
        selectedShopStructure = s;
    }

    public int getMapX() {
        return map.getGlobalX();
    }

    public int getMapY() {
        return map.getGlobalY();
    }

    public int getScale() {
        return map.getSize();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // exit if it is a right click
        if (e.getButton() == MouseEvent.BUTTON3) {
            return;
        }

        setMouseLocations(e.getX(), e.getY());

        // check if the mouse is in the shop
        if (shopScene.isOpen() && shopScene.contains(e.getX(), e.getY())) {
            isInShop = true;
            shopScene.mouseClicked(e);
        } else {
            isInShop = false;
        }

        // change the map tile if in edit mode
        if (edit) {
            if (globalMouseX >= 0 && globalMouseX < MAP_WIDTH / 32 && globalMouseY >= 0
                    && globalMouseY < MAP_HEIGHT / 32) {
                map.setTile(globalMouseX, globalMouseY, selectedTile.getId());
            }
        }

        // open/close the shop
        if (shopButton.contains(e.getX(), e.getY())) {
            if (shopScene.isOpen()) {
                isInShop = false;
                shopScene.setOpen(false);
                selectedShopStructure = null;
            } else {
                shopScene.setOpen(true);
            }
        }

        // place the selected shop structure
        if (selectedShopStructure != null && !isInShop) {
            structureManager.addBelt(selectedShopStructure, globalMouseX, globalMouseY);
        }

        // select a structure
        if (selectedShopStructure == null) {
            if (structureManager.getStructure(globalMouseX, globalMouseY) != null) {
                selectedStructure = structureManager.getStructure(globalMouseX, globalMouseY);
            }
        }

        // draw a belt from the selected belt
        if (selectedStructure != null) {
            if (selectedStructure.getType() >= Constants.BeltTypes.BELT_STRAIGHT
                    && selectedStructure.getType() <= Constants.BeltTypes.BELT_3IN) {
                for (int i = 0; i < availablePositions.length; i++) {
                    if (availablePositions[i][0] == -2) {
                        continue;
                    }

                    if (globalMouseX == selectedStructure.getX() + availablePositions[i][0]
                            && globalMouseY == selectedStructure.getY() + availablePositions[i][1]) {
                        structureManager.addBelt(selectedStructure, availablePositions[i], globalMouseX, globalMouseY);
                        selectedStructure = structureManager.getStructure(globalMouseX, globalMouseY);
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3 && !shopScene.contains(e.getX(), e.getY())) {
            game.setMoveCursor();
            rc = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        game.setMainCursor();
        shopButton.reset();
        rc = false;
        shopScene.mouseReleased(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setMouseLocations(e.getX(), e.getY());
        if (rc && !shopScene.contains(e.getX(), e.getY())) {
            game.setMoveCursor();
            map.pan(e.getX(), e.getY(), lastMouseX, lastMouseY);
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shopScene.isOpen() && shopScene.contains(e.getX(), e.getY())) {
            isInShop = true;
            shopScene.mouseMoved(e);
        } else {
            isInShop = false;
        }
        shopButton.reset();
        // update the mouse position
        setMouseLocations(e.getX(), e.getY());

        // update the shop button
        if (shopButton.contains(e.getX(), e.getY())) {

            shopButton.setHovered(true);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        map.scale(e.getWheelRotation(), globalMouseX, globalMouseY);
        globalMouseX = ((e.getX() - map.getGlobalX()) / map.getSize() * map.getSize()) / map.getSize();
        globalMouseY = ((e.getY() - map.getGlobalY()) / map.getSize() * map.getSize()) / map.getSize();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (localMouseX >= 0 && localMouseX <= GAME_WIDTH && localMouseY >= 0 && localMouseY <= GAME_HEIGHT
                && !isInShop && selectedShopStructure != null) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                selectedShopStructure.rotateC();

            }
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            if (shopScene.isOpen()) {
                shopScene.setOpen(false);
                selectedShopStructure = null;
            } else {
                shopScene.setOpen(true);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            structureManager.rotateStructure(selectedStructure, false);
        } else if (e.getKeyCode() == KeyEvent.VK_E) {
            structureManager.rotateStructure(selectedStructure, true);
        }

        // change the selected map tile if in edit mode
        if (edit) {
            if (e.getKeyCode() == KeyEvent.VK_0) {
                selectedTile = map.getTile(0);
            } else if (e.getKeyCode() == KeyEvent.VK_1) {
                selectedTile = map.getTile(1);
            } else if (e.getKeyCode() == KeyEvent.VK_2) {
                selectedTile = map.getTile(2);
            } else if (e.getKeyCode() == KeyEvent.VK_3) {
                selectedTile = map.getTile(3);
            }
        }
    }
}
