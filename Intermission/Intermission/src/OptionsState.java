import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class OptionsState extends GameState {

    private int currentchoice = 0;
    private int currentresolution;
    private List<Integer> resolutions;
    private Settings settings = new Settings();

    private Font titleFont = new Font("Lucida Console", Font.PLAIN, 50);
    private Font optionsFont = new Font("Lucida Console", Font.PLAIN, 30);
    private BufferedImage background;

    public OptionsState(GameStateManager gsm) {
        super(gsm);
        init();
        currentresolution = Gamepanel.panelsettings.getCurrentresolution();
    }

    public void init() {
        background = loadImage("imgs/background.jpg");
        setResolutions();
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

    public void setResolutions() {
        resolutions = new LinkedList<>();
        int[] allresolutions = {640, 480, 800, 600, 960, 720, 1024, 768, 1200, 900, 1280, 960, 1440, 1080};

        int i = 1;
        while (allresolutions[i] <= 900) {
            resolutions.add(allresolutions[i - 1]);
            resolutions.add(allresolutions[i]);
            i += 2;
        }
    }

    public void tick() {
    }

    public void draw(Graphics2D g, Settings settings, Point point) {
        g.drawImage(background, 0, 0, 640, 480, null);
        g.setFont(titleFont);
        g.setColor(Color.white);
        g.drawString("Options", 50, 70);

        g.setFont(optionsFont);
        g.setColor(Color.lightGray);
        g.drawString("Audio", 80, 140);
        g.drawString("Video", 80, 260);

        g.setStroke(new BasicStroke(3));
        g.setColor(currentchoice == 0 ? Color.red : Color.white);
        g.drawString("Music Volume", 100, 180);
        g.drawRect(370, 155, 215, 25);
        for (int i = 0; i < Game.settings.getBgvolume() * 10; i++) {
            g.fillRect(375 + i * 21, 160, 17, 16);
        }

        g.setColor(currentchoice == 1 ? Color.red : Color.white);
        g.drawString("SFX Volume", 100, 215);
        g.drawRect(370, 190, 215, 25);
        for (int i = 0; i < Settings.getSfxvolume() * 10; i++) {
            g.fillRect(375 + i * 21, 195, 17, 16);
        }

        g.setColor(currentchoice == 2 ? Color.red : Color.white);
        g.drawString("Fullscreen", 100, 300);
        g.drawString(settings.isfullscreen() ? "On" : "Off", 370, 300);

        g.setColor(currentchoice == 3 ? Color.red : (settings.isfullscreen() ? Color.gray : Color.white));
        g.drawString("Resolution", 100, 335);
        g.drawString(resolutions.get(currentresolution) + "x" + resolutions.get(currentresolution + 1), 370, 335);

        g.setColor(currentchoice == 4 ? Color.red : Color.white);
        g.drawString("Return", 80, 410);
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_W || k == KeyEvent.VK_UP) {
            currentchoice = (currentchoice == 4 && Gamepanel.panelsettings.isfullscreen() ? currentchoice - 1 : currentchoice);
            currentchoice = (currentchoice == 0 ? 4 : currentchoice - 1);
        }
        if (k == KeyEvent.VK_S || k == KeyEvent.VK_DOWN) {
            currentchoice = (currentchoice == 2 && Gamepanel.panelsettings.isfullscreen() ? currentchoice + 1 : currentchoice);
            currentchoice = (currentchoice == 4 ? 0 : currentchoice + 1);
        }
        if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
            if (currentchoice == 0) {
                Gamepanel.backgroundmusic.updateVolume();
                settings.lowerbgvolume();
            }
            if (currentchoice == 1) {
                Gamepanel.backgroundmusic.updateVolume();
                settings.lowersfxvolume();
            }
            if (currentchoice == 2) {
                if (!Gamepanel.panelsettings.isfullscreen()) {
                    Game.setfullscreen();
                    Gamepanel.panelsettings.setFullscreen(true);
                    Gamepanel.panelsettings.setScale(((float) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 480f));
                    Game.panel.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3 * 4), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight())));
                } else {
                    Game.setwindowed();
                    Gamepanel.panelsettings.setFullscreen(false);
                    Gamepanel.panelsettings.setScale(resolutions.get(currentresolution) / 640f);
                    Game.panel.setPreferredSize(new Dimension(resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left), resolutions.get(currentresolution + 1) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().top + Game.window.getInsets().bottom)));
                    Game.window.pack();
                    Game.window.setLocationRelativeTo(null);
                }
            }
            if (currentchoice == 3) {
                currentresolution = (currentresolution > 0 ? currentresolution - 2 : resolutions.size() - 2);
                Gamepanel.panelsettings.setScale(resolutions.get(currentresolution) / 640f);

                Game.panel.setPreferredSize(new Dimension(resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left),
                        resolutions.get(currentresolution + 1) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().top + Game.window.getInsets().bottom)));
                Game.window.pack();
                Game.window.setLocationRelativeTo(null);
            }
        }

        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
            if (currentchoice == 0) {
                Gamepanel.backgroundmusic.updateVolume();
                settings.increasebgvolume();
            }
            if (currentchoice == 1) {
                Gamepanel.backgroundmusic.updateVolume();
                settings.increasesfxvolume();
            }
            if (currentchoice == 2) {
                if (!Gamepanel.panelsettings.isfullscreen()) {
                    Game.setfullscreen();
                    Gamepanel.panelsettings.setFullscreen(true);
                    Gamepanel.panelsettings.setScale((float) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 480f);
                    Game.panel.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3 * 4), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight())));
                } else {
                    Game.setwindowed();
                    Gamepanel.panelsettings.setFullscreen(false);
                    Gamepanel.panelsettings.setScale((resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left)) / 640f);
                    Game.panel.setPreferredSize(new Dimension(resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left), resolutions.get(currentresolution + 1) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().top + Game.window.getInsets().bottom)));
                    Game.window.pack();
                    Game.window.setLocationRelativeTo(null);
                }
            }
            if (currentchoice == 3) {
                currentresolution = (currentresolution < resolutions.size() - 2 ? currentresolution + 2 : 0);
                Gamepanel.panelsettings.setScale((resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left)) / 640f);

                Game.panel.setPreferredSize(new Dimension(resolutions.get(currentresolution) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().right + Game.window.getInsets().left), resolutions.get(currentresolution + 1) - (Gamepanel.panelsettings.isfullscreen() ? 0 : Game.window.getInsets().top + Game.window.getInsets().bottom)));
                Game.window.pack();
                Game.window.setLocationRelativeTo(null);
            }
        }

        if (k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) {
            if (currentchoice == 4) {
                Gamepanel.panelsettings.setCurrentresolution(this.currentresolution);
                gsm.changestate(0);
            }
        }
        if (k == KeyEvent.VK_ESCAPE) {
            Gamepanel.panelsettings.setCurrentresolution(this.currentresolution);
            gsm.changestate(0);
        }
    }

    public void keyReleased(int k) {

    }
}