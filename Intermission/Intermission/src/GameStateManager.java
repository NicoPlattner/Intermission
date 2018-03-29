import java.awt.*;
import java.util.Stack;

public class GameStateManager {

    public Stack<GameState> gameStates;
    public Settings settings;

    public GameStateManager(Settings settings){
        this.settings = settings;
        gameStates = new Stack<GameState>();
        gameStates.push(new MenuState(this));
    }


    public void changestate(int newstate){
        if (newstate == 0){
            gameStates.pop();
        }
        if (newstate == 1){
            gameStates.push(new PlayState(this));
        }
        if (newstate == 2){
            gameStates.push(new OptionsState(this));
        }
        if (newstate == 3){
            gameStates.push(new ControlsState(this));
        }

    }


    public void tick(){
        gameStates.peek().tick();
    }

    public void draw(Graphics2D g, Settings settings, Point point){gameStates.peek().draw(g, settings, point);}

    public void keyPressed(int k){
        gameStates.peek().keyPressed(k);
    }

    public void keyReleased(int k){
        gameStates.peek().keyReleased(k);
    }
}