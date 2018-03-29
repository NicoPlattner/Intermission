import java.awt.*;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;
        init();
    }

    public abstract void init();
    public abstract void tick();
    public abstract void draw(Graphics2D g, Settings settings, Point point);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);
}
