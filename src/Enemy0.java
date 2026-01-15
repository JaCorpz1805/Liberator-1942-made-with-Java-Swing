public class Enemy0 {
    double x, y;
    double speed = 3.0;
    int shootCooldown = 0; // delay counter

    public Enemy0(int panelWidth) {
        this.x = Math.random() * (panelWidth - 45); // random horizontal position
        this.y = -45; //start  above  the screen
    }

    public  void update() {
        y += speed; // move down

        if (shootCooldown > 0) {
            shootCooldown--; // tick down
        }
    }

    public boolean canShoot() {
        return shootCooldown <= 0;
    }

    public void resetShootCooldown() {
        shootCooldown = 1000; // ~300ms (60 × 5ms per timer tick)
    }
}


