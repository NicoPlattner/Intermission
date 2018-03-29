import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Gamepanel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    public static Settings panelsettings;
    public Point mouseposition = new Point(-10, -10);

    // game thread
    private Thread thread;
    private boolean running;
    private final int fps = 60;
    private long targettime = 1000 / fps;

    // image
    private BufferedImage image;
    private Graphics2D g;

    //sound
    public static Sound backgroundmusic = new Sound("music/backgroundmusic2.wav");

    // game state manager
    private GameStateManager gsm = new GameStateManager(panelsettings);


    /*************************************************
     init
     ************************************************/


    public Gamepanel(Settings settings) {
        super();
        panelsettings = settings;

        setPreferredSize(new Dimension((int) (640 * panelsettings.scale), (int) (480 * panelsettings.scale)));
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        int d = (int) 0.5;

        start();
    }

    private void start() {
        running = true;
        thread = new Thread(this);
        thread.start();

        backgroundmusic.play(true);
    }


    /*************************************************
     run
     ************************************************/

    public void run() {
        long start, elapsed, wait;

        while (running) {
            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime() - start;
            wait = targettime - elapsed / 1000000;

            if (wait <= 0) {
                wait = 0;
            }

            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void tick() {
        setMouseposition();
        gsm.tick();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.scale(panelsettings.scale, panelsettings.scale);

        gsm.draw(g2d, panelsettings, mouseposition);
    }

    public static void setVolume(boolean increase) {
        if (increase) {
            Game.settings.increasebgvolume();
        } else {
            Game.settings.lowerbgvolume();
        }
    }

    /*************************************************
     Inputs
     ************************************************/


    public void setMouseposition() {
        if (getMousePosition() != null) {
            mouseposition = getMousePosition();
        }
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        gsm.keyPressed(e.getKeyCode());
    }

    public void keyReleased(KeyEvent e) {
        gsm.keyReleased(e.getKeyCode());
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e) {}

    public void mouseMoved(MouseEvent e) {}
}
