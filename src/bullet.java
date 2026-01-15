public class bullet  {
     double bx, by;
     double bulletspeed = 7;

     public bullet(double bx,  double by ) {
          this.bx = bx;
          this.by = by;
     }

     public void update() {
          by -= bulletspeed; // move upward
     }
}