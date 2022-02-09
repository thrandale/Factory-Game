package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpers.Constants.Colors;
import helpers.ImgEdit;

public class Button {
    private int x, y, width, height;
    private boolean isHovered, isPressed;
    private BufferedImage image;
    private int imgSize;
    private int type;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = null;
    }

    public Button(int x, int y, int width, int height, BufferedImage image, int type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.image = ImgEdit.scaleImage(image, width, height);
        this.imgSize = (int) (width * 0.8);
        this.type = type;

    }

    public void render(Graphics g) {
        int cornerRadius = (int) Math.min((Math.min(width, height) * .5), 30);
        if (isHovered) {
            g.setColor(Colors.BUTTON_HOVERED_COLOR);
        } else {
            g.setColor(Colors.BUTTON_COLOR);
        }
        g.fillRoundRect(x, y, width, height, cornerRadius, cornerRadius);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, width, height, cornerRadius, cornerRadius);

        if (image != null) {
            g.drawImage(image, x + (width - imgSize) / 2, y + (height - imgSize) / 2, imgSize, imgSize, null);
        }
    }

    public boolean contains(int x, int y) {
        if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height) {
            return true;
        }
        return false;
    }

    public void setHovered(boolean isHovered) {
        this.isHovered = isHovered;
    }

    public void onPress() {
        isPressed = true;
    }

    public void reset() {
        isHovered = false;
        isPressed = false;
    }

    public int getType() {
        return type;
    }

}
