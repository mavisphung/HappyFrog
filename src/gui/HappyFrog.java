/*
 * Author: HuyPC
 * Project: 
 */
package gui;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Admin
 */
public class HappyFrog implements ActionListener, KeyListener {

    public static final int WIDTH = 400, HEIGHT = 480;
    public static int FPS = 60;
    ArrayList<Player> playerList;
    Frog frog;
    JFrame frame;
    GamePanel panel;
    Rectangle rectUp, rectDown;
    int score, breakPoint, speed;
    Timer t;
    boolean paused;
    boolean pressed;
    boolean released;
    boolean gameOver;

    public void start() {
        frame = new JFrame("Happy Frog");
        frog = new Frog();
        rectUp = new Rectangle(WIDTH, 0, GamePanel.PIPE_W, (int) ((Math.random() * HEIGHT) / 5f + (0.18f) * HEIGHT));
        int h = (int) ((Math.random() * HEIGHT) / 5f + (0.18f) * HEIGHT);
        rectDown = new Rectangle(WIDTH, HEIGHT - h, GamePanel.PIPE_W, h);
        panel = new GamePanel(this, frog, rectUp, rectDown);

        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.add(panel);

        paused = true;
        pressed = false;
        released = true;
        gameOver = false;
        speed = 3;
        breakPoint = 10;
        playerList = new ArrayList();
        t = new Timer(1000 / FPS, this);
        t.start();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getScore() {
        return score;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (released) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                frog.jump();
                
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (paused) {
                    paused = false;
                } else {
                    paused = true;
                }
                
            }
            pressed = true;
            released = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        released = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panel.repaint();
        if (!paused) {
            frog.physics();
            //Random rd = new Random();
            Rectangle rectFrog = new Rectangle((int) frog.x, (int) frog.y, Frog.RAD * 2, Frog.RAD * 2);

            if (rectUp.x + rectUp.width < 0) {
                redraw();
            }

            if (rectUp.x + rectUp.width == rectFrog.x) {
                score += 1;
            }

            if (frog.y > HEIGHT || frog.y + frog.RAD < 0) {
                gameOver = true;
            }

            if (rectUp.intersects(rectFrog)
                    || rectDown.intersects(rectFrog)) {
                gameOver = true;
            }

            if (score != 0 && score % breakPoint == 0 && rectUp.x == WIDTH) {
                speed += 1;
                System.out.println("speed: " + speed);
            }
            
            rectUp.x -= speed;
            rectDown.x -= speed;

            
            if (gameOver) {
                String name;
                do {
                    name = JOptionPane.showInputDialog(null, "Please input your name");
                    if (name == null) {
                        name = "unknown";
                    }
                } while (name.isEmpty());
                
                Player newPlayer = new Player(name, score);
                playerList.add(newPlayer);
                writeFile("scoreboard.txt");
                frog.reset();
                redraw();
                paused = true;
                gameOver = false;
                score = 0;
                speed = 3;
            }

        } else {

        }
    }

    private void redraw() {
        rectUp.x = WIDTH;
        rectDown.x = WIDTH;

        rectUp.height = (int) ((Math.random() * HEIGHT) / 5f + (0.18f) * HEIGHT);
        int h = (int) ((Math.random() * HEIGHT) / 5f + (0.18f) * HEIGHT);
        rectDown.y = HEIGHT - h;
        rectDown.height = h;
    }

    private void writeFile(String filename) {
        PrintWriter w = null;
        try {
            w = new PrintWriter(filename);
            for (Player player : playerList) {
                w.append("Player: " + player.getName() + 
                        "\n\tScore: " + player.getScore() + "\n");
                int score = player.getScore();
                if (score < 10) {
                    w.append("\tReward: \n");
                } else if (score >= 10 && score < 20) {
                    w.append("\tReward: Bronze medal\n");
                } else if (score >= 20 && score < 30) {
                    w.append("\tReward: Silver medal\n");
                } else if (score >= 30 && score < 40) {
                    w.append("\tReward: Gold medal\n");
                } else {
                    w.append("\tReward: Platinum medal\n");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        } finally {
            try {
                if (w != null) {
                    w.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    public static void main(String[] args) {
        new HappyFrog().start();
    }
}
