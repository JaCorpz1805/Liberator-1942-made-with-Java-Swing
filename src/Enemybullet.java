public class Enemybullet {
    double bx, by;
    double speed = 3.5;

    public Enemybullet(double x, double y) {
        this.bx = x;
        this.by = y;
    }

    public void update() {
        by += speed;
    }
}
