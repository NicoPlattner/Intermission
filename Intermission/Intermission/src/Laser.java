public class Laser {

    int xposition;
    int yposition;
    int xposition2;
    int yposition2;
    int laserlenght;
    int rotation;


    public Laser(double x, double y, double laserrotation){
        rotation = (int) laserrotation;
        xposition = (int) x;
        yposition = (int) y;
        laserlenght = 20;
        setposition();
    }

    public void setposition(){
        xposition2 = (int) (xposition - laserlenght*Math.sin(Math.toRadians(rotation)));
        yposition2 = (int) (yposition + laserlenght*Math.cos(Math.toRadians(rotation)));
    }

    public void update(){
        xposition = xposition2;
        yposition = yposition2;

        xposition2 += laserlenght*Math.sin(Math.toRadians(rotation));
        yposition2 -= laserlenght*Math.cos(Math.toRadians(rotation));
    }

    public int getXposition() {
        return xposition;
    }

    public int getYposition() {
        return yposition;
    }

    public int getXposition2() {
        return xposition2;
    }

    public int getYposition2() {
        return yposition2;
    }
}
