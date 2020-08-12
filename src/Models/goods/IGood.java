package Models.goods;

import CollisionDetection.ICollisionObj;
import Game.Character;
import SoundEffects.SoundEffect;

public interface IGood extends ICollisionObj {
    SoundEffect sound = new SoundEffect();
    void pick(Character character);
}
