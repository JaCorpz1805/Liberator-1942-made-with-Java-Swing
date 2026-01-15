
import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class App {
    public static void main(String[] args) throws Exception {
        JFrame frame =  new JFrame();
        GameDes gamedes = new GameDes();
        ImageIcon image = new ImageIcon("src/plane.png");
        frame.setTitle("Liberator  1942");
        frame.setSize(500, 700);
        frame.setIconImage(image.getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamedes);
        
        frame.setVisible(true);

    }
}
