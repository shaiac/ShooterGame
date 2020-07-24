package CollisionDetection;

import java.lang.reflect.Type;

public abstract class CollisionData {
    public String type;
    public CollisionData(){
        type = getClass().getTypeName();
    }
}
