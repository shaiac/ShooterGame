package CollisionDetection;

public interface ICollisionObj {
    void collide(ICollisionObj obj);
    CollisionData getCollisionData();
}
