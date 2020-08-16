package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadingPage {
    private int i = 0;
    private BufferedImage smallImage;
    private BufferedImage image;
    private int height;
    private int width;
    private boolean reSized;
    private String backgroundPath;
    private Graphics g;

    public LoadingPage(Graphics g, int height, int width, String backgroundPath) {
        this.height = height;
        this.width = width;
        this.backgroundPath = backgroundPath;
        this.g = g;
        this.reSized = false;
        Font font = new Font("Helvetica", Font.PLAIN, 50);
        loadImages();
        g.drawImage(image, 0,0,width,height,null);
        g.setFont(font);
    }

    private void loadImages() {
        try {
            image = ImageIO.read(new File(backgroundPath));
            smallImage = ImageIO.read(new File("resources/loading_image_small.jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void changeI() {
        i++;
        i %= 3;
    }

    public void draw() {
        StringBuilder str = new StringBuilder("Loading");
        g.setColor(Color.white);
        if (i==0) {
            //g.fillRect((int)(width/2.5) + 180, height/2 - 30, 100, 50);
            g.drawImage(smallImage, (int)(width/2.5) + 180,height/2 - 30,100, 50,null);
        }
        for (int j = 0; j < i + 1; j++) {
            str.append(".");
        }
        g.setColor(Color.black);
        g.drawString(str.toString(), (int)(width/2.5), height/2);
        if (reSized) {
            g.drawImage(image, 0,0,width,height,null);
        }
    }

    public void setSize(Dimension dim) {
        height = dim.height;
        width = dim.width;
    }

    public void reSized() {
        this.reSized = true;
    }
}
