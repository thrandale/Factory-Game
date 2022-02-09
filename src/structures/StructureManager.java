package structures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpers.LoadSave;
import scenes.PlayScene;
import static helpers.Constants.GameConstants.*;
import static helpers.Constants.BeltTypes.*;

public class StructureManager {

    private PlayScene playScene;
    private BeltManager beltManager;
    private ArrayList<Structure> structures = new ArrayList<>();
    private int[][] structureMap = new int[MAP_WIDTH / 32][MAP_HEIGHT / 32];
    private int[][] structureRotMap = new int[MAP_WIDTH / 32][MAP_HEIGHT / 32];
    private int id;

    public StructureManager(PlayScene playScene) {
        this.playScene = playScene;
        init();
    }

    public void init() {
        resetMap(); 
        // loadMaps();
        beltManager = new BeltManager(playScene);
        addInitialStructures();
    }

    private void addInitialStructures() {
        for (int i = 0; i < structureMap.length; i++) {
            for (int j = 0; j < structureMap[i].length; j++) {
                if (structureMap[i][j] != -1) {
                    beltManager.addBelt(structureMap[i][j], structureRotMap[i][j], i, j, id++);
                }
            }
        }
        structures.addAll(beltManager.getBelts());
    }

    private void loadMaps() {
        structureMap = LoadSave.loadMap("structureMap");
        structureRotMap = LoadSave.loadMap("structureRotMap");
    }

    public void render(Graphics g) {
        beltManager.render(g);
    }

    public BufferedImage getBeltSprite(int rotation) {
        return beltManager.getBeltSprite(rotation);
    }

    public void update() {
        beltManager.update();
    }

    public void addBelt(Structure structure, int globalMouseX, int globalMouseY) {
        beltManager.addBelt(structure.getType(), structure.getRotation(), globalMouseX, globalMouseY, id++);
        structures.add(beltManager.getBelt(globalMouseX, globalMouseY));
    }

    public void addBelt(Structure structure, int[] adjPos, int globalMouseX, int globalMouseY) {
        int[] rotations = calcNewBelt(structure, adjPos);

        // add the new belt
        beltManager.addBelt(BELT_STRAIGHT, rotations[1], globalMouseX, globalMouseY, id++);
        structures.add(beltManager.getBelt(globalMouseX, globalMouseY));
    }

    private int[] calcNewBelt(Structure belt, int[] adjPos) {
        int[] rotations = new int[2];
        boolean isConnected = false;

        // check if the space behind the belt is empty
        switch (belt.getRotation()) {
            case 0:
                if (beltManager.getBelt(belt.getX(), belt.getY() + 1) != null) {
                    isConnected = true;
                }
                break;
            case 1:
                if (beltManager.getBelt(belt.getX() - 1, belt.getY()) != null) {
                    isConnected = true;
                }
                break;
            case 2:
                if (beltManager.getBelt(belt.getX(), belt.getY() - 1) != null) {
                    isConnected = true;
                }
                break;
            case 3:
                if (beltManager.getBelt(belt.getX() + 1, belt.getY()) != null) {
                    isConnected = true;
                }
                break;
        }

        // if not connected rotate the old belt to match the new one
        if (!isConnected) {
            if (adjPos[0] < 0) {
                rotations[0] = 3;
                rotations[1] = 3;
            } else if (adjPos[0] > 0) {
                rotations[0] = 1;
                rotations[1] = 1;
            } else if (adjPos[1] < 0) {
                rotations[0] = 0;
                rotations[1] = 0;
            } else if (adjPos[1] > 0) {
                rotations[0] = 2;
                rotations[1] = 2;
            }
        } else {
            // if it is connected, change the belt if needed
            if (adjPos[0] < 0) {
                if (belt.getRotation() == 0) {
                    rotations[0] = 2;
                    rotations[1] = 3;
                    beltManager.changeBeltType(belt, BELT_CORNER_CC, rotations[0]);
                } else if (belt.getRotation() == 2) {
                    rotations[0] = 3;
                    rotations[1] = 3;
                    beltManager.changeBeltType(belt, BELT_CORNER_C, rotations[0]);
                } else if (belt.getRotation() == 1) {
                    rotations[0] = 1;
                    rotations[1] = 1;
                } else if (belt.getRotation() == 3) {
                    rotations[0] = 3;
                    rotations[1] = 3;
                }
            } else if (adjPos[0] > 0) {
                if (belt.getRotation() == 0) {
                    rotations[0] = 1;
                    rotations[1] = 1;
                    beltManager.changeBeltType(belt, BELT_CORNER_C, rotations[0]);
                } else if (belt.getRotation() == 2) {
                    rotations[0] = 0;
                    rotations[1] = 1;
                    beltManager.changeBeltType(belt, BELT_CORNER_CC, rotations[0]);
                } else if (belt.getRotation() == 1) {
                    rotations[0] = 1;
                    rotations[1] = 1;
                } else if (belt.getRotation() == 3) {
                    rotations[0] = 3;
                    rotations[1] = 3;
                }
            } else if (adjPos[1] < 0) {
                if (belt.getRotation() == 1) {
                    rotations[0] = 3;
                    rotations[1] = 0;
                    beltManager.changeBeltType(belt, BELT_CORNER_CC, rotations[0]);
                } else if (belt.getRotation() == 3) {
                    rotations[0] = 0;
                    rotations[1] = 0;
                    beltManager.changeBeltType(belt, BELT_CORNER_C, rotations[0]);
                } else if (belt.getRotation() == 0) {
                    rotations[0] = 0;
                    rotations[1] = 0;
                } else if (belt.getRotation() == 2) {
                    rotations[0] = 2;
                    rotations[1] = 2;
                }
            } else if (adjPos[1] > 0) {
                if (belt.getRotation() == 1) {
                    rotations[0] = 2;
                    rotations[1] = 2;
                    beltManager.changeBeltType(belt, BELT_CORNER_C, rotations[0]);
                } else if (belt.getRotation() == 3) {
                    rotations[0] = 1;
                    rotations[1] = 2;
                    beltManager.changeBeltType(belt, BELT_CORNER_CC, rotations[0]);
                } else if (belt.getRotation() == 0) {
                    rotations[0] = 0;
                    rotations[1] = 0;
                } else if (belt.getRotation() == 2) {
                    rotations[0] = 2;
                    rotations[1] = 2;
                }
            }

        }

        beltManager.rotateBelt(belt, rotations[0]);
        return rotations;
    }

    public void resetMap() {
        for (int x = 0; x < structureMap.length; x++) {
            for (int y = 0; y < structureMap[0].length; y++) {
                structureMap[x][y] = -1;
                structureRotMap[x][y] = 0;
            }
        }
    }

    public ArrayList<Belt> getBelts() {
        return beltManager.getBelts();
    }

    public Structure getStructure(int x, int y) {
        for (Structure s : structures) {
            if (s.getX() == x && s.getY() == y) {
                return s;
            }
        }
        return null;
    }

    public int[][] getAvailablePositions(Structure structure) {
        // get the available belt positions
        int[][] availablePositions = new int[3][2];
        int rotation = structure.getRotation();
        int[] x;
        int[] y;

        switch (rotation) {
            case 0:
                x = new int[] { -1, 0, 1 };
                y = new int[] { 0, -1, 0 };
                break;
            case 1:
                x = new int[] { 0, 1, 0 };
                y = new int[] { -1, 0, 1 };
                break;
            case 2:
                x = new int[] { -1, 0, 1 };
                y = new int[] { 0, 1, 0 };
                break;
            case 3:
                x = new int[] { 0, -1, 0 };
                y = new int[] { -1, 0, 1 };
                break;
            default:
                x = new int[] { 0, 0, 0 };
                y = new int[] { 0, 0, 0 };
                break;
        }

        for (int i = 0; i < 3; i++) {
            if (getStructure(x[i], y[i]) == null) {
                availablePositions[i][0] = x[i];
                availablePositions[i][1] = y[i];
            } else {
                availablePositions[i][0] = -2;
                availablePositions[i][1] = -2;
            }
        }

        return availablePositions;
    }

    public void saveMap() {
        for (Structure s : structures) {
            structureMap[s.getX()][s.getY()] = s.getType();
            structureRotMap[s.getX()][s.getY()] = s.getRotation();
        }
        LoadSave.saveMap(structureMap, "structureMap");
        LoadSave.saveMap(structureRotMap, "structureRotMap");
    }

    public void rotateStructure(Structure structure, boolean clockWise) {
        int rotation = structure.getRotation();
        if (clockWise) {
            rotation++;
        } else {
            rotation--;
        }

        if (rotation > 3) {
            rotation = 0;
        } else if (rotation < 0) {
            rotation = 3;
        }

        beltManager.rotateBelt(structure, rotation);
    }
}
