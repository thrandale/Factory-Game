package structures;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;

import helpers.Constants;
import helpers.ImgEdit;
import helpers.LoadSave;
import scenes.PlayScene;

import static helpers.Constants.SpriteSheet.*;

public class BeltManager {
    private BufferedImage spriteSheet;
    private BufferedImage beltImgs[][][] = new BufferedImage[7][4][4];
    private ArrayList<Belt> belts = new ArrayList<>();
    private PlayScene playScene;
    private int beltSpeed = Constants.GameConstants.DEFAULT_ANIMATION_SPEED;
    private int animationIndex = 0;
    private int updates = 0;

    public Belt beltStraight;
    public Belt beltCornersC;
    public Belt beltCornersCC;
    public Belt belt2InAdjC;
    public Belt belt2InAdjCC;
    public Belt belt2InOpp;
    public Belt belt3In;

    private BufferedImage[][] beltStraightSprites = new BufferedImage[4][4];
    private BufferedImage[][] beltCornersCSprites = new BufferedImage[4][4];
    private BufferedImage[][] beltCornersCCSprites = new BufferedImage[4][4];
    private BufferedImage[][] belt2InAdjCSprites = new BufferedImage[4][4];
    private BufferedImage[][] belt2InAdjCCSprites = new BufferedImage[4][4];
    private BufferedImage[][] belt2InOppSprites = new BufferedImage[4][4];
    private BufferedImage[][] belt3InSprites = new BufferedImage[4][4];

    public BeltManager(PlayScene playScene) {
        this.playScene = playScene;
        loadSpriteSheet();
        loadStructureImgs();
    }

    private void loadSpriteSheet() {
        spriteSheet = LoadSave.getSpriteSheet();
    }

    private void loadStructureImgs() {
        // straight belts (d-u, l-r, u-d, r-l)
        beltStraightSprites[0] = getRotatedSprites(0, BELT_STRAIGHT_POS, 90);
        beltStraightSprites[1] = getRotatedSprites(0, BELT_STRAIGHT_POS, 180);
        beltStraightSprites[2] = getRotatedSprites(0, BELT_STRAIGHT_POS, 270);
        beltStraightSprites[3] = getSprites(0, BELT_STRAIGHT_POS);
        beltImgs[0] = beltStraightSprites;

        // corner belts clockwise (r-t, b-r, l-b, t-l)
        beltCornersCSprites[0] = getSprites(0, BELT_CORNER_POS);
        beltCornersCSprites[1] = getRotatedSprites(0, BELT_CORNER_POS, 90);
        beltCornersCSprites[2] = getRotatedSprites(0, BELT_CORNER_POS, 180);
        beltCornersCSprites[3] = getRotatedSprites(0, BELT_CORNER_POS, 270);
        beltImgs[1] = beltCornersCSprites;

        // // corner belts counter-clockwise (t-r, r-b, b-l, l-t)
        beltCornersCCSprites[0] = getFlippedRotatedSprites(0, BELT_CORNER_POS, 270);
        beltCornersCCSprites[1] = getFlippedRotatedSprites(0, BELT_CORNER_POS, 180);
        beltCornersCCSprites[2] = getFlippedRotatedSprites(0, BELT_CORNER_POS, 90);
        beltCornersCCSprites[3] = getFlippedRotatedSprites(0, BELT_CORNER_POS, 0);
        beltImgs[2] = beltCornersCCSprites;

        // belts with 2 inputs adjacent clockwise
        belt2InAdjCSprites[0] = getSprites(0, BELT_2IN_ADJ_POS);
        belt2InAdjCSprites[1] = getRotatedSprites(0, BELT_2IN_ADJ_POS, 90);
        belt2InAdjCSprites[2] = getRotatedSprites(0, BELT_2IN_ADJ_POS, 180);
        belt2InAdjCSprites[3] = getRotatedSprites(0, BELT_2IN_ADJ_POS, 270);
        beltImgs[3] = belt2InAdjCSprites;

        // // belts with 2 inputs adjacent counter-clockwise
        belt2InAdjCCSprites[0] = getFlippedRotatedSprites(0, BELT_2IN_ADJ_POS, 270);
        belt2InAdjCCSprites[1] = getFlippedRotatedSprites(0, BELT_2IN_ADJ_POS, 180);
        belt2InAdjCCSprites[2] = getFlippedRotatedSprites(0, BELT_2IN_ADJ_POS, 90);
        belt2InAdjCCSprites[3] = getFlippedRotatedSprites(0, BELT_2IN_ADJ_POS, 0);
        beltImgs[4] = belt2InAdjCCSprites;

        // // belts with 2 inputs opposite
        belt2InOppSprites[0] = getSprites(0, BELT_2IN_OPP_POS);
        belt2InOppSprites[1] = getRotatedSprites(0, BELT_2IN_OPP_POS, 90);
        belt2InOppSprites[2] = getRotatedSprites(0, BELT_2IN_OPP_POS, 180);
        belt2InOppSprites[3] = getRotatedSprites(0, BELT_2IN_OPP_POS, 270);
        beltImgs[5] = belt2InOppSprites;

        // // belts with 3 inputs
        belt3InSprites[0] = getSprites(0, BELT_3IN_POS);
        belt3InSprites[1] = getRotatedSprites(0, BELT_3IN_POS, 90);
        belt3InSprites[2] = getRotatedSprites(0, BELT_3IN_POS, 180);
        belt3InSprites[3] = getRotatedSprites(0, BELT_3IN_POS, 270);
        beltImgs[6] = belt3InSprites;
    }

    public void render(Graphics g) {
        for (Belt belt : belts) {
            g.drawImage(beltImgs[belt.getType()][belt.getRotation()][animationIndex],
                    belt.getX() * playScene.getScale() + playScene.getMapX(),
                    belt.getY() * playScene.getScale() + playScene.getMapY(), playScene.getScale(),
                    playScene.getScale(), null);
        }
    }

    public void update() {
        updates++;
        if (updates % beltSpeed == 0) {
            animationIndex++;
            if (animationIndex > 3) {
                animationIndex = 0;
            }
        }
    }

    public void addBelt(int type, int rotation, int x, int y, int id) {
        belts.add(new Belt(x, y, rotation, type, id));
    }

    private BufferedImage[] getSprites(int x, int y) {
        BufferedImage[] sprites = new BufferedImage[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = spriteSheet.getSubimage((x + i) * 32, y * 32, 32, 32);
        }
        return sprites;
    }

    private BufferedImage[] getRotatedSprites(int x, int y, int rotation) {
        BufferedImage[] sprites = new BufferedImage[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = ImgEdit.getRotatedImage(spriteSheet.getSubimage((x + i) * 32, y * 32, 32, 32), rotation);
        }
        return sprites;
    }

    private BufferedImage[] getFlippedRotatedSprites(int x, int y, int rotation) {
        BufferedImage[] sprites = new BufferedImage[4];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = ImgEdit.getFlippedRotatedImage(spriteSheet.getSubimage((x + i) * 32, y * 32, 32, 32),
                    rotation);
        }
        return sprites;
    }

    public Belt getBelt(int x, int y) {
        for (Belt b : belts) {
            if (b.getX() == x && b.getY() == y) {
                return b;
            }
        }
        return null;
    }

    public ArrayList<Belt> getBelts() {
        return belts;
    }

    public BufferedImage getBeltSprite(int rotation) {
        return beltImgs[0][rotation][0];
    }

    public void rotateBelt(Structure structure, int rotation) {
        for (Belt b : belts) {
            if (b.getX() == structure.getX() && b.getY() == structure.getY()) {
                b.setRotation(rotation);
            }
        }
    }

    public void changeBeltType(Structure structure, int type, int rotation) {
        for (Belt b : belts) {
            if (b.getX() == structure.getX() && b.getY() == structure.getY()) {
                b.setType(type, rotation);
            }
        }
    }
}
