/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package CollisionDetection;

public interface ICollisionObj {
    void collide(ICollisionObj obj);
    CollisionData getCollisionData();
}
