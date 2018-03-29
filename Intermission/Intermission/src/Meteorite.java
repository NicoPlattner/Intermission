import java.awt.geom.Path2D;

public class Meteorite {

    double xposition;
    double yposition;
    double deltax;
    double deltay;
    double angle;
    double speed;
    double rotation = 0;
    double rotationspeed;

    Hitbox hitbox;

    public Meteorite(int x, int y, double width, double height) {
        xposition = x;
        yposition = y;
        angle = Math.random() * 40 - 20;
        speed = Math.random() + 2;
        rotation = 0;
        rotationspeed = (Math.random() - 0.5) * 4;

        deltax = Math.sin(Math.toRadians(angle)) * speed;
        deltay = Math.cos(Math.toRadians(angle)) * speed;

        double[] verticesx = new double[]{xposition + 6, xposition + 20, xposition + 35, xposition + 50, xposition + 50, xposition + 30, xposition + 7};
        double[] verticesy = new double[]{yposition + 13, yposition + 3, yposition + 10, yposition + 13, yposition + 46, yposition + 53, yposition + 38};

        hitbox = new Hitbox(verticesx, verticesy, xposition, yposition, angle, speed, rotation, rotationspeed, width, height);
    }

    public void update() {
        yposition += deltay;
        xposition += deltax;
        rotation += rotationspeed;

        hitbox.rotate(rotationspeed);
        hitbox.move(deltax, deltay);
    }

    public Path2D getHitbox() {
        return hitbox.getpolygon();
    }

    public double getXposition() {
        return xposition;
    }

    public double getYposition() {
        return yposition;
    }

    public double getRotation() {
        return rotation;
    }
}
