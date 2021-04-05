/*
 * Author: HuyPC
 * Project: 
 */
package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class GamePanel extends JPanel {
    public static final int PIPE_W = 50, PIPE_H = 30;
    
    Frog frog;
    HappyFrog hf;
    Font scoreFont, pauseFont;
    Image pipeUp, pipeDown, bg;
    Rectangle rectUp, rectDown;
    
    public GamePanel(HappyFrog hf, Frog frog, Rectangle rectUp, Rectangle rectDown) {
        this.hf = hf;
        this.frog = frog;
        this.rectUp = rectUp;
        this.rectDown = rectDown;
        
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 28);
        this.setSize(HappyFrog.WIDTH, HappyFrog.HEIGHT);
        
        try {
            pipeUp = ImageIO.read(new File("tube2.png"));
            pipeDown = ImageIO.read(new File("tube.png"));
            bg = ImageIO.read(new File("background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, HappyFrog.WIDTH, HappyFrog.HEIGHT, null);
        frog.draw(g);
        g.drawImage(pipeUp, rectUp.x, rectUp.y, rectUp.width, rectUp.height, null);
        g.drawImage(pipeDown, rectDown.x, rectDown.y, rectDown.width, rectDown.height, null);
        
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: " + hf.getScore(), 10, 20);
        
        if (hf.isPaused()) {
            g.setFont(pauseFont);
            g.drawString("PAUSED", HappyFrog.WIDTH / 2 - 50, HappyFrog.HEIGHT / 2 - 100);
            g.drawString("PRESS SPACE TO BEGIN", HappyFrog.WIDTH / 2 - 175, HappyFrog.HEIGHT / 2 + 50);
        }
    }
    
    
}
