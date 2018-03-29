import java.awt.*;

public class Smoke {

    int xposition;
    int yposition;
    int diameter;
    int alpha = 50;
    Color smokecolor = new Color(120,120,120,alpha);

    public Smoke(int x, int y){
        xposition = x;
        yposition = y;
        diameter = 20;
    }

    public void increaseDiameter(){
        diameter +=2;
        xposition -= 1;
    }

    public Color getSmokecolor() {
        return smokecolor;
    }

    public int getXposition() {
        return xposition;
    }

    public int getYposition() {
        return yposition;
    }

    public int getDiameter() {
        return diameter;
    }
}
