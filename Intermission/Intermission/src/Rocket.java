import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Rocket {

    double xposition, yposition;
    double maxy;
    double rotation;
    double speed;
    double acceleration;
    int direction;
    int lastkey;
    Polygon hitbox;
    int[] verticesx;
    int[] verticesy;
    Rectangle2D hit;
    boolean shield;
    int shieldcount;


    public Rocket(int rocketx, int rockety, int currentkey) {
        xposition = rocketx;
        yposition = rockety + 100;
        maxy = rockety;
        direction = currentkey;
        speed = 0;
        acceleration = 0.4;
        shield = true;

        verticesx = new int[]{(int) xposition + 15, (int) xposition + 20, (int) xposition + 20, (int) xposition + 15};
        verticesy = new int[]{(int) yposition, (int) yposition, (int) yposition + 30, (int) yposition + 30};
        hitbox = new Polygon(verticesx, verticesy, 4);

        hit = new Rectangle((int) xposition + 15, (int) yposition + 15, 5, 10);
    }

    public void setLocation(int key) {
        setRotation();
        setposition(key);
        setDirection(key);
        setSpeed();
    }

    public void setposition(int direction) {
        if (shieldcount <= 100) {
            shieldcount++;
        } else {
            shield = false;
        }

        if (yposition > maxy) {
            yposition -= 3;
        }

        if (direction == 0 && (lastkey == KeyEvent.VK_LEFT || lastkey == KeyEvent.VK_A)) {
            if (speed < 0 && xposition >= 20) {
                speed -= acceleration;
                xposition += speed;
            }
            if (speed > 0 && xposition >= 20) {
                speed += acceleration;
                xposition += speed;
            }
        }
        if (direction == 0 && (lastkey == KeyEvent.VK_RIGHT || lastkey == KeyEvent.VK_D)) {
            if (speed > 0 && xposition <= 580) {
                speed -= acceleration;
                xposition += speed;
            }
            if (speed < 0 && xposition <= 580) {
                speed += acceleration;
                xposition += speed;
            }
        }
        if (direction == KeyEvent.VK_LEFT || direction == KeyEvent.VK_A) {
            acceleration = -0.4;
            lastkey = direction;
            if (speed <= 8 && speed >= -8) {
                speed += acceleration;
            }
            if (xposition >= 20 && xposition <= 580) {
                xposition += speed;
            }
            if (speed < -8) {
                speed = -8;
            }
        }

        if (direction == KeyEvent.VK_RIGHT || direction == KeyEvent.VK_D) {
            acceleration = 0.4;
            lastkey = direction;

            if (speed <= 8 && speed >= -8) {
                speed += acceleration;
            }
            if (speed > 8) {
                speed = 8;
            }
            if (xposition >= 20 && xposition <= 580) {
                xposition += speed;
            }
        }
        if (xposition > 580) {
            xposition = 580;
        }
        if (xposition < 20) {
            xposition = 20;
        }

        verticesx = new int[]{(int) xposition + 9, (int) xposition + 32, (int) xposition + 32, (int) xposition + 9};
        verticesy = new int[]{(int) yposition + 7, (int) yposition + 7, (int) yposition + 50, (int) yposition + 50};
        hitbox = new Polygon(verticesx, verticesy, 4);

        hit = new Rectangle((int) xposition + 9, (int) yposition + 7, 23, 47);
    }

    public void setSpeed() {
        this.speed = Math.round(speed * 10) / 10.0;
    }

    public void setRotation() {
        rotation = speed / 8 * 30;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Polygon getHitbox() {
        return hitbox;
    }

    public double getRotation() {
        return rotation;
    }

    public double getXposition() {
        return xposition;
    }

    public double getYposition() {
        return yposition;
    }

    public boolean isShield() {
        return shield;
    }

    public Rectangle2D getHit() {
        return hit;
    }
}
