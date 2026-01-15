public class Fireball0 {
    double x, y;
    int timer = 20; // frames until explosion disappears (~100ms at 5ms timer)

    public Fireball0(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        timer--;
    }

    public boolean isDone() {
        return timer <= 0;
    }
}
