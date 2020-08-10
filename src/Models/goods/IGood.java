package Models.goods;

import CollisionDetection.ICollisionObj;
import Game.Character;

public interface IGood extends ICollisionObj {
    void pick(Character character);
}
