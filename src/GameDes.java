import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameDes extends JPanel implements ActionListener, KeyListener{

    private Image backgroundImage;
    private Image PlaneImage;
    private Image UserBulletImage;
    private Image EnemyPlaneImage;
    private Image EnemyBulletImage;
    private Image FireballImage;
    private Image ExplosionImage;

    private int bg1 = 0; //First background y position
    private int bg2; //Second background y position
    private int bgspeed = 2; //scroll speed
    private int ExplosionTimer = 0;
    private int lives = 3; 
    private int score = 0;
    private int lastEnemyX = -100; // store the last enemy's X position
    private int minSpacing = 80; // minimum distance between enemies

    private List<bullet> bullets = new ArrayList<>();
    private List<Enemy0> enemyplane0 = new ArrayList<>(); 
    private List<Enemybullet> enemybullet = new ArrayList<>();
    private List<Fireball0> Fireball = new ArrayList<>();

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean UserBulletShot =  false;
    private boolean userExploded = false;
    private boolean gameOver = false;

    private int cooldown = 0; // counter to control firing rate
    private int enemyspawntimer = 200;

   
    Timer t =  new Timer(5, this);
    double userx = 230 , usery = 570, uservelx = 0, uservely = 0;

    public GameDes() {
        backgroundImage = new ImageIcon(getClass().getResource("/background.png")).getImage();
        UserBulletImage = new ImageIcon(getClass().getResource("/UserBullet.png")).getImage();
        EnemyPlaneImage = new ImageIcon(getClass().getResource("/enemy0.png")).getImage();
        EnemyBulletImage = new ImageIcon(getClass().getResource("/enemyBullet.png")).getImage();
        FireballImage = new ImageIcon(getClass().getResource("/fireball0.png")).getImage();
        ExplosionImage = new ImageIcon(getClass().getResource("/explosion0.png")).getImage();
        bg2 = -backgroundImage.getHeight(null);
        userplane();
    }

    public void userplane(){
        PlaneImage = new ImageIcon(getClass().getResource("/plane.png")).getImage();
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private void triggerExplosion() {
        userExploded = true;
        ExplosionTimer = 50; // frames explosion stays visible
        lives--;
    }

    private void restartGame() {
        // Reset Variables
        lives = 3;
        score = 0;
        bullets.clear();
        enemyplane0.clear();
        enemybullet.clear();
        userx = getWidth() / 2 - 20;
        usery = getHeight() - 80;
        userExploded = false;
        gameOver = false;
        enemyspawntimer = 200;

        // Restart timer
        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {  // Use paintComponent, not paint
        super.paintComponent(g); // Clear panel
        g.drawImage(backgroundImage, 0, bg1, this); // Draw image scaled to panel size
        g.drawImage(backgroundImage, 0, bg2, this);
        g.drawImage(PlaneImage, (int) userx, (int) usery, 45, 45, this);

        //User bullets
        for (bullet b : bullets){
        g.drawImage(UserBulletImage, (int) b.bx-10, (int) b.by, 25, 20, this);
        }

        //enemy
        for (Enemy0 en : enemyplane0){
        g.drawImage(EnemyPlaneImage, (int) en.x, (int) en.y, 45, 45, this);
        }

        //enemy bullet
        for (Enemybullet eb : enemybullet){
            g.drawImage(EnemyBulletImage, (int) eb.bx-2, (int) eb.by, 9, 15, this);
        }

        //Fireball for enemy
        for (Fireball0 fb : Fireball) {
            g.drawImage(FireballImage, (int) fb.x,(int) fb.y, 45, 45, this);
        }

        //For user Explosion
        if (userExploded) {
            g.drawImage(ExplosionImage, (int) userx-10, (int) usery, 60, 60, this);
        } else {
            g.drawImage(PlaneImage, (int) userx, (int) usery, 45, 45, this);
        }

        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, 20, 55);

        if (gameOver) {
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString("GAME OVER", getWidth() / 2 - 100, getHeight() / 2);
            g.setFont(g.getFont().deriveFont(18f));
            g.drawString("Press 'R' to restart", getWidth() / 2 - 90, getHeight() / 2 + 40);
            return; // stop drawing anything else
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
       if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
           leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downPressed = true;
        } 
        if (code == KeyEvent.VK_SPACE) {
            UserBulletShot = true;
        }
        if (code == KeyEvent.VK_R) {
            restartGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            upPressed = false;
            uservely = 0;
        }
        if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
           leftPressed = false;
           uservelx = 0;
        }
        if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightPressed = false;
            uservelx = 0;
        }
        if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downPressed = false;
            uservely = 0;
        } 
        if (code == KeyEvent.VK_SPACE) {
           UserBulletShot = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
    //update background positions
    bg1 += bgspeed;
    bg2 += bgspeed;

    //First background y position
    if (bg1 >= getHeight()) {
        bg1 = bg2 - backgroundImage.getHeight(null);
    }
    if (bg2 >= getHeight()) {
        bg2 = bg1 - backgroundImage.getHeight(null);
    }

    userx += uservelx;
    usery += uservely;

     

    if(upPressed)  {uservely = -4.0;}
    if(downPressed) {uservely = 4.0;}
    if(leftPressed) {uservelx = -7.0;}
    if(rightPressed) {uservelx = 7.0;}

    // Prevent plane from going outside the panel bounds
    if (userx < 0) userx = 0;
    if (usery < 0) usery = 0;
    if (userx > getWidth() - 45) userx = getWidth() - 45;
    if (usery > getHeight() - 45) usery = getHeight() - 45;

    // Shooting
    if (UserBulletShot) {
         if (cooldown <= 0) { // only fire when space is still held
            bullets.add(new bullet(userx + 20, usery - 10));
            cooldown = 15; // reset cooldown
        } else {
            cooldown--; // tick cooldown only if shooting
        }
    } else {
        cooldown = 0; // reset immediately when key is released
    }

    if (cooldown > 0) {cooldown--;};

    if (userExploded) {
        ExplosionTimer--;
        if (ExplosionTimer <= 0) {
            if (lives > 0) {
                //Respawn plane
                userx = getWidth() / 2-20;
                usery = getHeight() - 80;
                userExploded = false;
            } else {
                //Game Over
                gameOver = true;
                t.stop();
            }
        }
    }

    //update bullets
    Iterator<bullet> it = bullets.iterator();
    while  (it.hasNext()) {
        bullet b = it.next();
        b.update();
        if (b.by < 0) {it.remove();}
    }

    //Enemy spawn timer
    if (enemyspawntimer > 0) {
        enemyspawntimer--;
    }
    else {
        int enemyCount = (int) (Math.random() * 2) + 2; // spawn 2–3 enemies

    for (int i = 0; i < enemyCount; i++) {
        int newX;
        do {
            newX = (int)(Math.random() * (getWidth() - 45));
        } while (Math.abs(newX - lastEnemyX) < minSpacing);

        enemyplane0.add(new Enemy0(newX));
        lastEnemyX = newX; // update last enemy's X
    }
    enemyspawntimer = 200; // reset timer (about 1 sec at 5ms timer)
    }

   // Update enemies independently 
    Iterator<Enemy0> enemyiter = enemyplane0.iterator();
    while (enemyiter.hasNext()) {
    Enemy0 en = enemyiter.next();
    en.update();

     // Simple shooting: fire every 60 frames (≈300ms with Timer 5ms)
     if(Math.random() < 0.02) {
        enemybullet.add(new Enemybullet(en.x + 20, en.y + 40));
     }

    // remove enemy if it goes off screen
    if (en.y > getHeight()) {
        enemyiter.remove();
    }
}

// Then check collisions separately
Iterator<bullet> bulletIter = bullets.iterator();
while (bulletIter.hasNext()) {
    bullet b = bulletIter.next();

    Iterator<Enemy0> enemyiter2 = enemyplane0.iterator();
    while (enemyiter2.hasNext()) {
        Enemy0 en = enemyiter2.next();

        if (b.bx < en.x + 45 && b.bx + 20 > en.x &&
            b.by < en.y + 45 && b.by + 20 > en.y) {
            bulletIter.remove();
            enemyiter2.remove();
            score += 100; // add a score for each enemy destroyed
            Fireball.add(new Fireball0(en.x, en.y));
            break;
        }
      }
    }

    Iterator<Enemybullet> enemybulIter = enemybullet.iterator();
    while (enemybulIter.hasNext()) {
        Enemybullet eb = enemybulIter.next();
        eb.update();
        if (eb.by > getHeight()){
            enemybulIter.remove();
        } else if (!userExploded &&
               eb.bx < userx + 45 && eb.bx + 10 > userx &&
               eb.by < usery + 45 && eb.by + 20 > usery) {
                triggerExplosion();
               }
    }

    Iterator<Fireball0> FballIter = Fireball.iterator();
    while (FballIter.hasNext()) {
        Fireball0 fb = FballIter.next();
        fb.update();
        if (fb.isDone()){
            FballIter.remove();
        }
    }
    
    repaint();
    }

    
}