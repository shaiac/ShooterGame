package Game;/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import LinearMath.Vector;

public class CoordinateSystem {
 private Vector x;
 private Vector y;
 private Vector z;
 private Vector origin;
 private Vector lastOrigin;

 public CoordinateSystem() {
        double[] vec = {0,5,0};
        origin = new Vector(vec,3 );
        double[] vecZ = {0, 0, 1};
        z = new Vector(vecZ, 3);
        double[] vecX = {1, 0, 0};
        x = new Vector(vecX, 3);
        double[] vecY = {0, 1, 0};
        y = new Vector(vecY, 3);
        lastOrigin = origin;
 }

 public void rotate(char axis, double angle) {
     if (axis == 'x') {
         z = z.Multiply(Math.cos(angle)).minus(y.Multiply(Math.sin(angle)));
         z = z.normal();
         y = y.Multiply(Math.cos(angle)).Add(z.Multiply(Math.sin(angle)));
         y = y.normal();
     } if (axis == 'y') {
         Vector tempX = new Vector(x.getVec(),3);
         x = x.Multiply(Math.cos(angle)).Add(z.Multiply(Math.sin(angle)));
         x = x.normal();
         z = z.Multiply(Math.cos(angle)).minus(tempX.Multiply(Math.sin(angle)));
         z = z.normal();
     } if (axis == 'z') {
         x = x.Multiply(Math.cos(angle)).Add(y.Multiply(Math.sin(angle)));
         x = x.normal();
         y = y.Multiply(Math.cos(angle)).minus(x.Multiply(Math.sin(angle)));
         y = y.normal();
     }
 }

 public void moveStep(char axis, double step) {
     lastOrigin = origin;
     if (axis == 'x') {
        origin = origin.Add(x.Multiply(step));
     } if (axis == 'y') {
         origin = origin.Add(y.Multiply(step));
     } if (axis == 'z') {
         origin = origin.Add(z.Multiply(step));
     }

 }
 public void moveStep(double[] step){
     lastOrigin = origin;
     origin = origin.Add(x.Multiply(step[0]));
     origin = origin.Add(y.Multiply(step[1]));
     origin = origin.Add(z.Multiply(step[2]));
 }
 public void revertStep(){
     origin = lastOrigin;

 }

 public Vector getOrigin() {
     return this.origin;
 }
 public Vector getX() {
     return this.x;
 }
 public Vector getY() {
     return this.y;
 }
 public Vector getZ() {
     return this.z;
 }
}
