import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class Hitbox {

    double xposition;
    double yposition;
    double width;
    double height;
    double angle;
    double speed;
    double rotation;
    double rotationspeed;

    Path2D hitbox = new Path2D.Double();


    public Hitbox(double[] verticesx, double[] verticesy, double xposition, double yposition, double angle, double speed, double rotation, double rotationspeed, double width, double height) {
        this.xposition = xposition;
        this.yposition = yposition;
        this.width = width;
        this.height = height;
        this.angle = angle;
        this.speed = speed;
        this.rotation = rotation;
        this.rotationspeed = rotationspeed;

        boolean first = true;
        for (int i = 0; i < verticesx.length; i++) {
            if (first) {
                hitbox.moveTo(verticesx[0], verticesy[0]);
                first = false;
            } else {
                hitbox.lineTo(verticesx[i], verticesy[i]);
            }
        }
        hitbox.closePath();
    }

    public void move(double deltax, double deltay) {
        this.xposition += deltax;
        this.yposition += deltay;

        AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

        at.translate(deltax, deltay);
        hitbox.transform(at);
    }

    public void rotate(double degrees){
        AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

        at.rotate(Math.toRadians(degrees), xposition + width / 2, yposition + height / 2);
        hitbox.transform(at);
    }


    public Path2D getpolygon() {
        return hitbox;
    }
}
