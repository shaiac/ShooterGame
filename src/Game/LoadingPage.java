package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadingPage {
    private Graphics g;
    private Font font;
    private int i = 0;

    private BufferedImage image;

    public LoadingPage(Canvas canvas) {
        this.g = canvas.getGraphics();
        this.font = new Font("Helvetica", Font.PLAIN, 50);
        try {
            image = ImageIO.read(new File("resources/sand_glass.png"));
        } catch (Exception e) {
            System.out.println(e);
        }
        g.setFont(font);
    }

    public void changeI() {
        i++;
        i %= 3;
    }

    public void draw() {
        StringBuilder str = new StringBuilder("Loading");
        g.setColor(Color.white);
        if (i==0) {
            g.fillRect(480, 270, 100, 50);
        }
        for (int j = 0; j < i + 1; j++) {
            str.append(".");
        }
        g.setColor(Color.black);
        g.drawString(str.toString(), 300, 300);
        //g.drawImage(image, 0,0,null);
    }
}
