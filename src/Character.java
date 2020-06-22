import Models.IModel;

import java.util.List;
import java.util.Queue;

public class Character {
    CoordinateSystem coordinateSystem;
    Queue<IModel> weapons;
    int ammu;
    IModel current;

    public void changeWeapon(){
        if(weapons.isEmpty()){
            return;
        }
        weapons.add(current);
        current = weapons.poll();
        /*if(current.type == "gun"){
            ammu--;
        }*/
    }
    public void attack(){
        //current.attack();
    }
    public void addAmmu(int quantity){
        ammu += quantity;
    }
}
