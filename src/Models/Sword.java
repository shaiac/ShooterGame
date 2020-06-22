package Models;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;

public class Sword extends Model {
    String path;
    public Sword(String inPath){
        this.path = inPath;
    }
    public void create(ObjectLoader loader,GL2 gl){
        data = loader.LoadModelToGL(path,gl);
    }
    @Override
    public void draw(GL2 gl) {
        for (ObjData obj:data) {
            obj.draw(gl);
        }

    }
}
