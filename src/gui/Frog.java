package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
 * Author: HuyPC
 * Project: 
 */

/**
 *
 * @author Admin
 */
public class Frog {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;
    
    public Frog() {
        x = HappyFrog.WIDTH/2 - 2*RAD;
        y = HappyFrog.HEIGHT/2;
        try {
            img = ImageIO.read(new File("frog.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void physics() {
        x+=vx;
        y+=vy;
        vy+=0.5f;
    }
    
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, (int) x, (int) y,2*RAD,2*RAD, null);
    }
    
    public void jump() {
        vy = -8;
    }
    
    public void reset() {
        x = HappyFrog.WIDTH/2 - 2*RAD;
        y = HappyFrog.HEIGHT/2;
        vx = vy = 0;
    }
}
