package Models;

import Game.ShooterGame;
import Levels.Level;
import Models.DataAndLoader.ObjData;
import java.util.ArrayList;
import java.util.List;

public abstract class Model implements IModel {
    protected List<ObjData> data = new ArrayList<>();
    protected float[] startPos = {0,0,0};
    protected Level observer;
    protected String path;
//    public Model(ShooterGame shooterGame) {
//        this.observer = shooterGame;
//    }
    @Override
    public void translate(float x, float y, float z) {
        float[] trans = {x,y,z};
        for (ObjData obj:data) {
            obj.Translate(trans);
        }
    }

    @Override
    public void rotate(float angle, char axis) {
        if(axis == 'x'){
            for (ObjData obj:data) {
                obj.setAngleX(angle);
            }
        }if(axis == 'y'){
            for (ObjData obj:data) {
                obj.setAngleY(angle);
            }
        }if(axis == 'z'){
            for (ObjData obj:data) {
                obj.setAngleZ(angle);
            }
        }
    }

    @Override
    public void scale(float x, float y, float z) {
        float[] scale = {x,y,z};
        for (ObjData obj:data) {
            obj.Scale(scale);
        }
    }

    public String getPath() {
        return path;
    }
}
