import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Composite;
import java.awt.AlphaComposite;

public class Explosion {
    double x,y;
    int lifetime = 40; // How long will the explosion stay(frames)
    int age = 0; //how many frames passed
    double scale = 0.4; // starting size
    double maxScale = 2.0; // how big it grows

    private static Image image = new ImageIcon(
        Explosion.class.getResource("/explosion0.png")).getImage();
    
    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        age++;
        if (scale < maxScale){ // make it grow until 2x size
            scale += (maxScale - 0.4) / lifetime;
        }
    }

    public boolean isFinished() {
        return age >= lifetime;
    }

    public void draw(Graphics g, JPanel panel) {
       Graphics2D g2 =  (Graphics2D) g;
       Composite orig = g2.getComposite();

       //alpha fades out over lifetime (1.0 -> 0.0)
       float alpha = 1f - ((float) age / (float) lifetime);
       if (alpha < 0f) alpha = 0f;

       g2. setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

       int imgW = image.getWidth(null);
       int imgH = image. getHeight(null);
       int drawW = (int) (imgW * scale);
       int drawH = (int) (imgH  * scale);

       //center the explosion at (x,y)
       g2.drawImage(image, (int) (x - drawW / 2), (int) (y - drawH / 2), drawW, drawH, panel);
  
       g2.setComposite(orig);
    }
}
