import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ControlsState extends GameState {

    private Font titleFont = new Font("Lucida Console", Font.PLAIN, 50);
    private Font optionsFont = new Font("Lucida Console", Font.PLAIN, 30);

    private BufferedImage background;
    private BufferedImage leftbutton;
    private BufferedImage rightbutton;
    private BufferedImage spacebutton;

    public ControlsState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        background = loadImage("imgs/background.jpg");
        leftbutton = loadImage("imgs/Left-Button.png");
        rightbutton = loadImage("imgs/Right-button.png");
        spacebutton = loadImage("imgs/Space-button.png");
    }

    public BufferedImage loadImage(String filename) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void tick() {
    }

    public void draw(Graphics2D g, Settings settings, Point point) {
        g.drawImage(background, 0, 0, 640, 480, null);

        g.setColor(Color.white);
        g.setFont(titleFont);
        g.drawString("Controls", 50,70);
        g.setFont(optionsFont);
        g.drawString("Move", 100,150);
        g.drawString("Shoot", 100,210);

        g.setFont(optionsFont);
        g.setColor(Color.red);
        g.drawString("Return", 80, 410);

        g.drawImage(leftbutton, 220,120,40,40, null);
        g.drawImage(rightbutton, 270,120,40,40, null);
        g.drawImage(spacebutton, 220,180,200,40, null);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_ENTER ||k==KeyEvent.VK_SPACE) {
            gsm.gameStates.pop();
        }
    }

    public void keyReleased(int k) {}
}
