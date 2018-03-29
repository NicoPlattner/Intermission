import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuState extends GameState {


    private int currentchoice = 0;
    private String[] options = {"Start", "Controls", "Options", "Quit"};

    private Font titleFont = new Font("Lucida Console", Font.PLAIN, 50);
    private BufferedImage rocket;
    private BufferedImage background;
    private BufferedImage logo;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        init();
    }

    public void init() {
        rocket = loadImage("imgs/rocket.png");
        background = loadImage("imgs/background.jpg");
        logo = loadImage("imgs/Intermission.png");
    }

    public void tick() {
    }

    public BufferedImage loadImage(String filename) {
        BufferedImage img = null;

        try{
            img = ImageIO.read(getClass().getResourceAsStream(filename));
        } catch (IOException e){
            e.printStackTrace();
        }

        return img;
    }

    public void draw(Graphics2D g, Settings settings, Point point) {

        g.drawImage(background, 0, 0, 640, 480, null);
        g.drawImage(logo, 70,50,500,70, null);

        AffineTransform at = AffineTransform.getTranslateInstance(-130, 100);
        at.rotate(Math.toRadians(0));
        at.scale(0.5, 0.5);

        g.drawImage(rocket, at, null);

        g.setFont(titleFont);
        for (int i = 0; i < options.length; i++) {
            if (i == currentchoice) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.white);
            }
            g.drawString(options[i], 170, i * 50 + 210);
        }
    }


    public void keyPressed(int k) {
        if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) {
            currentchoice--;
            if (currentchoice < 0) {
                currentchoice = 3;
            }
        }
        if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) {
            currentchoice++;
            if (currentchoice > 3) {
                currentchoice = 0;
            }
        }
        if (k == KeyEvent.VK_ENTER ||k==KeyEvent.VK_SPACE) {
            if (currentchoice == 0) {
                gsm.changestate(1);
            }
            if (currentchoice == 1) {
                gsm.changestate(3);
            }
            if (currentchoice == 2) {
                gsm.changestate(2);
            }
            if (currentchoice == 3) {
                System.exit(0);
            }
        }
        if (k == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }


    public void keyReleased(int k) {
    }
}
