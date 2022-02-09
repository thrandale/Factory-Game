package structures;

import java.awt.image.BufferedImage;

public abstract class Structure {
    protected BufferedImage sprites[][];
    protected int structureType;
    protected int rotation;
    protected int animationIndex = 0;
    protected int updates;
    protected int cost;
    protected int x, y;
    protected int id;

    public Structure(int x, int y, int structureType, int rotation, int cost, int id) {
        this.x = x;
        this.y = y;
        this.structureType = structureType;
        this.cost = cost;
        this.rotation = rotation;
        this.id = id;
    }

    public int getType() {
        return structureType;
    }

    public void rotateC() {
        rotation = (rotation + 1) % 4;
    }

    public void rotateCC() {
        rotation = (rotation + 3) % 4;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public boolean contains(int globalMouseX, int globalMouseY) {
        return globalMouseX >= x * 32 && globalMouseX <= (x + 1) * 32 && globalMouseY >= y * 32
                && globalMouseY <= (y + 1) * 32;
    }

    public int getId() {
        return id;
    }
}
