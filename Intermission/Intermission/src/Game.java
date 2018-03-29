import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.Frame.MAXIMIZED_BOTH;
import static java.awt.Frame.NORMAL;

public class Game {

    public static Settings settings = new Settings();
    public static JPanel panel = new Gamepanel(settings);
    public static JFrame window = new JFrame("Game");
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    public Game(){
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(new GridBagLayout());
        window.add(panel, new GridBagConstraints());

        if (settings.fullscreen) {
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setUndecorated(false);
        window.setResizable(true);

        if (settings.fullscreen){
            window.setUndecorated(true);
        }

        window.getContentPane().setCursor(blankCursor);
        window.validate();
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


    public static void setfullscreen() {
        window.dispose();
        window.setUndecorated(true);
        panel.setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
        window.setExtendedState(MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    public static void setwindowed() {
        window.dispose();
        window.setUndecorated(false);
        window.setState(NORMAL);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}
