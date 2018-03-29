import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class PlayState extends GameState {

    private BufferedImage rocket;
    private BufferedImage meteorite;
    private BufferedImage fragment;
    private BufferedImage background;
    private Font scorefont = new Font("Lucida Console", Font.PLAIN, 20);
    private Font gameoverfont = new Font("Lucida Console", Font.PLAIN, 60);
    private Font yourscorefont = new Font("Lucida Console", Font.PLAIN, 30);
    private Font optionsfont = new Font("Lucida Console", Font.PLAIN, 40);
    private Font namesfont = new Font("Lucida Console", Font.PLAIN, 25);
    private Color shieldcolor = new Color(115, 243, 243, 90);
    private Color highscoresbgcolor = new Color(127, 127, 127, 150);

    public static Sound lasersound = new Sound("sfx/lasersound.wav");
    public static Sound explosionsound = new Sound("sfx/explosionsound.wav");

    int rocketx = 640 / 2 - 20;
    int rockety = 480 / 10 * 8;
    int currentkey;

    int rocketxleft = 5;
    int rocketxright = 5;

    int score;
    int lives = 3;
    int meteoritecount;
    double meteoritespersecond;
    int lasercount;
    boolean running = true;
    boolean hint = false;
    int gameoveroption = 0;
    long start;
    String playername = "";

    Rocket player = new Rocket(rocketx, rockety, currentkey);
    LinkedList<Smoke> currentSmoke = new LinkedList<>();
    LinkedList<Laser> laserlist = new LinkedList<>();
    LinkedList<Meteorite> meteoritelist = new LinkedList<>();
    LinkedList<Fragment> fragmentlist = new LinkedList<>();
    FileHandler fileHandler = new FileHandler();

    public PlayState(GameStateManager gsm) {
        super(gsm);

        fileHandler = new FileHandler();

        init();
    }

    public void init() {
        start = System.currentTimeMillis();
        rocket = loadImage("imgs/rocket.png");
        background = loadImage("imgs/background.jpg");
        fragment = loadImage("imgs/fragment.png");
        meteorite = loadImage("imgs/meteorite.png");
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

    public void playsound(Sound sound) {
        sound.play(false);
    }

    public void setSmoke() {
        currentSmoke.add(new Smoke((int) (player.getXposition() - player.getRotation() * 0.7), (int) (player.getYposition() + 8 - Math.abs(player.getRotation()) * 0.3)));

        for (int i = 0; i < currentSmoke.size(); i++) {
            currentSmoke.get(i).increaseDiameter();
            currentSmoke.get(i).yposition += 3;
        }
        if (currentSmoke.size() == 20) {
            currentSmoke.poll();
        }
    }

    public void tick() {
        if (running) {
            player.setLocation(currentkey);
            setSmoke();
            updatelasers();
            updatefragments();
            setmeteorites();
            checkcollision();

            score += (running ? 3 : 0);
        }
    }

    public void checkcollision() {
        int temp = 0;

        for (int i = 0; i < meteoritelist.size(); i++) {
            for (int j = 0; j < laserlist.size(); j++) {
                if (meteoritelist.get(i).getHitbox().contains(laserlist.get(j).getXposition(), laserlist.get(j).getYposition())) {
                    fragmentlist.push(new Fragment(meteoritelist.get(i).getXposition(), meteoritelist.get(i).getYposition(), fragment.getWidth() * 0.2, fragment.getHeight() * 0.2));
                    fragmentlist.push(new Fragment(meteoritelist.get(i).getXposition(), meteoritelist.get(i).getYposition(), fragment.getWidth() * 0.2, fragment.getHeight() * 0.2));
                    fragmentlist.push(new Fragment(meteoritelist.get(i).getXposition(), meteoritelist.get(i).getYposition(), fragment.getWidth() * 0.2, fragment.getHeight() * 0.2));
                    meteoritelist.remove(i);
                    laserlist.remove(j);
                    score += 300;
                }
            }

            if (!player.isShield()) {
                if (meteoritelist.get(i).getHitbox().intersects(player.getHit())) {
                    if (!player.isShield()) {
                        playsound(explosionsound);
                        lives--;
                        player = new Rocket(rocketx, rockety, currentkey);
                    }

                    if (lives == 0) {
                        running = false;
                    }
                } else {
                    temp++;
                }
            }
        }

        for (int i = 0; i < fragmentlist.size(); i++) {
            if (!fragmentlist.isEmpty()) {
                if (fragmentlist.get(i).getHitbox().intersects(player.getHit())) {
                    if (!player.isShield()) {
                        playsound(explosionsound);
                        lives--;
                        player = new Rocket(rocketx, rockety, currentkey);

                        if (lives == 0) {
                            running = false;
                        }
                    }

                } else {
                    temp++;
                }
            }

            for (int j = 0; j < laserlist.size(); j++) {
                if (fragmentlist.get(i).getHitbox().contains(laserlist.get(j).getXposition(), laserlist.get(j).getYposition())) {
                    fragmentlist.remove(i);
                    laserlist.remove(j);
                    score += 1000;
                }
            }
        }
    }

    public void setmeteorites() {
        meteoritecount++;

        if (meteoritelist.size() > 35) {
            meteoritelist.poll();
        }

        if ((20 - 0.3 * (System.currentTimeMillis() - start) / 1000) > 12) {
            meteoritespersecond = (int) (20 - 0.3 * (System.currentTimeMillis() - start) / 1000);
        } else {
            meteoritespersecond = 10;
        }

        if (meteoritecount >= (int) meteoritespersecond) {
            meteoritelist.add(new Meteorite((int) (Math.random() * 640), -100, meteorite.getWidth() * 0.1, meteorite.getHeight() * 0.1));
            meteoritecount = 0;
        }

        for (int i = 0; i < meteoritelist.size(); i++) {
            meteoritelist.get(i).update();
        }
    }

    private void updatefragments() {
        for (int i = 0; i < fragmentlist.size(); i++) {
            fragmentlist.get(i).update();
        }
    }

    private void updatelasers() {
        lasercount++;
        if (lasercount >= 30) {
            lasercount = 30;
        }

        for (int i = 0; i < laserlist.size(); i++) {
            laserlist.get(i).update();
        }

        for (int i = 0; i < laserlist.size(); i++) {
            if (laserlist.get(i).getYposition()< 0) {
                laserlist.remove(i);
            }
        }
    }

    public void draw(Graphics2D g, Settings settings, Point point) {
        g.drawImage(background, 0, 0, 640, 480, null);

        AffineTransform at = AffineTransform.getTranslateInstance(player.getXposition(), player.getYposition());

        at.scale(0.07, 0.07);
        at.rotate(Math.toRadians(player.getRotation()), rocket.getWidth() / 2, rocket.getHeight() / 2);

        g.setColor(Color.yellow);
        //  g.fillOval((int) point.getX(), (int) point.getY(), 10, 10);
        //  g.draw(player.hit);

        if (running) {
            for (int i = 0; i < currentSmoke.size(); i++) {
                g.setColor(currentSmoke.get(i).getSmokecolor());
                g.fillOval(currentSmoke.get(i).getXposition() + 12, currentSmoke.get(i).getYposition() + 40, currentSmoke.get(i).getDiameter(), currentSmoke.get(i).getDiameter());
            }

            for (int i = 0; i < laserlist.size(); i++) {
                g.setColor(Color.green);
                g.setStroke(new BasicStroke(3));
                g.drawLine(laserlist.get(i).getXposition(), laserlist.get(i).getYposition(), laserlist.get(i).getXposition2(), laserlist.get(i).getYposition2());
            }
        }

        for (int i = 0; i < meteoritelist.size(); i++) {
            AffineTransform at2 = AffineTransform.getTranslateInstance(meteoritelist.get(i).getXposition(), meteoritelist.get(i).getYposition());
            at2.scale(0.1, 0.1);
            at2.rotate(Math.toRadians(meteoritelist.get(i).getRotation()), meteorite.getWidth() / 2, meteorite.getHeight() / 2);

            g.drawImage(meteorite, at2, null);

            g.setColor(Color.cyan);
            //     g.draw(meteoritelist.get(i).getHitbox());
        }

        for (int i = 0; i < fragmentlist.size(); i++) {
            AffineTransform at2 = AffineTransform.getTranslateInstance(fragmentlist.get(i).getXposition(), fragmentlist.get(i).getYposition());
            at2.scale(0.2, 0.2);
            at2.rotate(Math.toRadians(fragmentlist.get(i).getRotation()), fragment.getWidth() / 2, fragment.getHeight() / 2);

            g.drawImage(fragment, at2, null);

            g.setColor(Color.cyan);
            // g.draw(fragmentlist.get(i).getHitbox());
        }

        g.drawImage(rocket, at, null);
        if (player.isShield()) {
            g.setColor(shieldcolor);
            g.fillOval((int) player.getXposition() - 8, (int) player.getYposition(), 60, 60);
        }

        if (running) {
            g.setColor(Color.green);
            g.setFont(scorefont);
            g.drawString(Integer.toString(score), 560, 60);
        } else {
            g.setColor(Color.white);
            g.setFont(gameoverfont);
            g.drawString("GAME OVER", 140, 90);
            g.setColor(Color.green);
            g.setFont(yourscorefont);
            g.drawString("YOUR SCORE: " + score, 170, 130);
            g.setColor(gameoveroption == 0 ? Color.white : Color.gray);
            g.setFont(optionsfont);
            g.drawString("REPLAY", 120, 450);
            g.setColor(gameoveroption == 1 ? Color.white : Color.gray);
            g.drawString("QUIT", 390, 450);

            List<Integer> scores = fileHandler.getScores();
            List<String> names = fileHandler.getNames();

            g.setColor(highscoresbgcolor);
            g.fillRect(70, 170, 500, 200);
            g.fillRect(70, 170, 500, 35);
            g.setFont(yourscorefont);
            g.setColor(Color.white);
            g.drawString("Highscores", 220, 197);

            g.setColor(Color.white);

            boolean added = false;
            int scoreposition = 203;

            g.setFont(namesfont);

            if (hint) {
                g.drawString("Please enter your name", Game.panel.getWidth() / 2 - 170, 400);
            }

            if (names.size() == 0) {
                scoreposition += 30;

                if (System.currentTimeMillis() % 1500 <= 700 && playername.length() < 10) {
                    g.drawString(playername + "_", 170, scoreposition);
                } else {
                    g.drawString(playername, 170, scoreposition);
                }

                g.drawString("1.", 100, scoreposition);
                g.drawString(score + "", 440, scoreposition);
            } else {
                for (int i = 0; i < ((score > scores.get(scores.size() - 1) && names.size() >= 5) ? names.size() : names.size()); i++) {
                    if (!added && scores.get(i) < score) {
                        scoreposition += 30;

                        g.drawString((i + 1) + ".", 100, scoreposition);

                        if (System.currentTimeMillis() % 1500 <= 700 && playername.length() < 10) {
                            g.drawString(playername + "_", 170, scoreposition);
                        } else {
                            g.drawString(playername, 170, scoreposition);
                        }

                        g.drawString(score + "", 440, scoreposition);

                        added = true;
                    }
                    scoreposition += 30;

                    if (i < ((score > scores.get(scores.size() - 1) && names.size() >= 5) ? names.size() - 1 : names.size())) {
                        g.drawString((added ? (i + 2) : (i + 1)) + ".", 100, scoreposition);
                        g.drawString(names.get(i), 170, scoreposition);
                        g.drawString(scores.get(i) + "", 440, scoreposition);
                    }

                }

                if (!added && names.size() < 5) {
                    scoreposition += 30;
                    g.drawString((names.size() + 1) + ".", 100, scoreposition);

                    if (System.currentTimeMillis() % 1500 <= 700 && playername.length() < 10) {
                        g.drawString(playername + "_", 170, scoreposition);
                    } else {
                        g.drawString(playername, 170, scoreposition);
                    }

                    g.drawString(score + "", 440, scoreposition);
                }
            }
        }

        if (lives >= 1) {
            AffineTransform at3 = AffineTransform.getTranslateInstance(25, 30);
            at3.scale(0.04, 0.04);
            g.drawImage(rocket, at3, null);
        }
        if (lives >= 2) {
            AffineTransform at4 = AffineTransform.getTranslateInstance(55, 30);
            at4.scale(0.04, 0.04);
            g.drawImage(rocket, at4, null);
        }
        if (lives >= 3) {
            AffineTransform at5 = AffineTransform.getTranslateInstance(85, 30);
            at5.scale(0.04, 0.04);
            g.drawImage(rocket, at5, null);
        }
    }

    public void keyPressed(int k) {
        if (!running && Character.isAlphabetic((char) k) && playername.length() < 10) {
            playername += (char) k;
        }
        if (!running && k == KeyEvent.VK_BACK_SPACE) {
            String temp = "";
            for (int i = 0; i < playername.length() - 1; i++) {
                temp += playername.charAt(i);
            }
            playername = temp;
        }

        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) {
            currentkey = k;
        }
        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT) {
            rocketx += rocketxleft;
        }
        if (k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT) {
            rocketx -= rocketxright;
        }
        if (k == KeyEvent.VK_LEFT && !running) {
            if (!fileHandler.getScores().isEmpty()) {
                if (playername.length() >= 3 || score <= fileHandler.getScores().get(fileHandler.getScores().size() - 1)) {
                    gameoveroption = 0;
                }
            }
        }
        if (k == KeyEvent.VK_RIGHT && !running) {
            if (!fileHandler.getScores().isEmpty()) {
                if (playername.length() >= 3 || score <= fileHandler.getScores().get(fileHandler.getScores().size() - 1)) {
                    gameoveroption = 1;
                }
            }
        }
        if (k == KeyEvent.VK_SPACE) {
            if (lasercount >= 30) {
                playsound(lasersound);
                laserlist.add(new Laser(player.getXposition() + 20 + player.getRotation(), player.getYposition(), player.getRotation()));
                lasercount = 0;
            }
        }
        if (k == KeyEvent.VK_ESCAPE) {
            gsm.gameStates.pop();
        }
        if ((k == KeyEvent.VK_ENTER || k == KeyEvent.VK_SPACE) && !running) {
            if (fileHandler.getScores().size() >= 5) {
                if (score <= fileHandler.getScores().get(fileHandler.getScores().size() - 1)) {
                    if (gameoveroption == 0) {
                        fileHandler.writeHighscores(playername, score);
                        gsm.gameStates.pop();
                        gsm.changestate(1);
                    }
                    if (gameoveroption == 1) {
                        fileHandler.writeHighscores(playername, score);
                        gsm.gameStates.pop();
                    }
                } else {
                    if (playername.length() >= 3) {
                        if (gameoveroption == 0) {
                            fileHandler.writeHighscores(playername, score);
                            gsm.gameStates.pop();
                            gsm.changestate(1);
                        }
                        if (gameoveroption == 1) {
                            fileHandler.writeHighscores(playername, score);
                            gsm.gameStates.pop();
                        }
                    } else {
                        hint = true;
                    }
                }
            } else {
                if (playername.length() >= 3) {
                    if (gameoveroption == 0) {
                        fileHandler.writeHighscores(playername, score);
                        gsm.gameStates.pop();
                        gsm.changestate(1);
                    }
                    if (gameoveroption == 1) {
                        fileHandler.writeHighscores(playername, score);
                        gsm.gameStates.pop();
                    }
                } else {
                    hint = true;
                }
            }
        }
    }

    public void keyReleased(int k) {

        if (k == KeyEvent.VK_D || k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_A || k == KeyEvent.VK_LEFT || k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_BACK_SPACE) {
            currentkey = 0;
        }
    }
}