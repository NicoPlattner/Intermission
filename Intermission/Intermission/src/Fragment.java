import java.awt.geom.Path2D;

public class Fragment {

    double xposition;
    double yposition;
    double deltax;
    double deltay;
    double angle;
    double speed;
    double rotation = 0;
    double rotationspeed;

    Hitbox hitbox;

    public Fragment(double x, double y, double width, double height) {
        xposition = x;
        yposition = y;
        angle = Math.random() * 180 - 90;
        speed = Math.random() + 2;
        rotation = 0;
        rotationspeed = (Math.random() - 0.5) * 4;

        deltax = Math.sin(Math.toRadians(angle)) * speed;
        deltay = Math.cos(Math.toRadians(angle)) * speed;

        double[] verticesx = new double[]{xposition + 3, xposition + 12, xposition + 30, xposition + 30, xposition + 17, xposition + 3};
        double[] verticesy = new double[]{yposition + 13, yposition + 3, yposition + 10, yposition + 35, yposition + 40, yposition + 30};

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
