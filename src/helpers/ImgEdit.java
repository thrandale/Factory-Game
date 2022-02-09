package helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class ImgEdit {
    public static BufferedImage getRotatedImage(BufferedImage img, int rotation) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage newImg = new BufferedImage(h, w, img.getType());
        Graphics2D g2d = newImg.createGraphics();

        g2d.rotate(Math.toRadians(rotation), w / 2, h / 2);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();

        return newImg;
    }

    public static BufferedImage getFlippedRotatedImage(BufferedImage img, int rotation) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage newImg = new BufferedImage(h, w, img.getType());
        Graphics2D g2d = newImg.createGraphics();

        g2d.rotate(Math.toRadians(rotation), w / 2, h / 2);
        g2d.drawImage(img, null, 0, 0);
        g2d.dispose();

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-w, 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        newImg = op.filter(newImg, null);

        return newImg;
    }

    public static BufferedImage scaleImage(BufferedImage img, int newWidth, int newHeight) {
        BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g2d = newImg.createGraphics();
        g2d.drawImage(img, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return newImg;
    }

}
