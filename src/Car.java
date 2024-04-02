import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
    /**
      *This class is used to bring object and track to be read to the gamewindo.java
      * @Author Chris, Ramiz,Jin
     */
public class Car {
    private BufferedImage image;
    private double angle = 0;
    private int initialX, initialY;
    private int endX, endY;
    private int width, height;
    private boolean hasReachedEnd = false;

    public Car(String imagePath, int initialX, int initialY, int endX, int endY, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            this.image = resizeImage(originalImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.initialX = initialX;
        this.initialY = initialY;
        this.endX = endX;
        this.endY = endY;
        this.width = width;
        this.height = height;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    public void rotate(int deltaAngle) {
        angle += Math.toRadians(deltaAngle);
    }

    public void moveForwardOrBackward(int distance) {
        int vertical = (int) (distance * Math.cos(angle));
        int horizontal = (int) (distance * Math.sin(angle));
        this.initialY += vertical;
        this.initialX -= horizontal;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int centerX = initialX + width / 2;
        int centerY = initialY + height / 2;

        AffineTransform at = AffineTransform.getTranslateInstance(initialX, initialY);
        at.rotate(angle, centerX - initialX, centerY - initialY);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();
    }


    //  end position for timer to check
    public boolean hasReachedEnd() {
        // Define the end position coordinates here
        int endX = 1000;
        int endY = 1000;

        // Calculate the distance between the current position and the end position
        double distance = Math.sqrt(Math.pow(endX - initialX, 2) + Math.pow(endY - initialY, 2));

        // Check if the distance is less what required
        if (distance < 10) {
            hasReachedEnd = true;
        } else {
            hasReachedEnd = false;
        }
        return hasReachedEnd;
    }
    // Getters and setters
    public int getInitialX() {
        return initialX;
    }
    public int getInitialY() {
        return initialY;
    }
    public int getEndX(){
        return endX;
    }
    public int getEndY(){
        return endY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}
