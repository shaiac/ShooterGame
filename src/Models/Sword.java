package Models;

import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Sword extends Model {
    String path;
    private boolean picked;
    public Sword(String inPath){
        this.path = inPath;
    }
    public void create(ObjectLoader loader,GL2 gl){
        data = loader.LoadModelToGL(path,gl);
    }
    @Override
    public void draw(GL2 gl) {
        if(!picked){
            rotate(0.2f,'x');
            rotate(0.4f,'y');
            rotate(0.3f,'z');
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }

    }
}
