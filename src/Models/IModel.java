/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import CollisionDetection.CollisionData;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import javafx.beans.Observable;

import javax.media.opengl.GL2;
import java.util.List;

public interface IModel {
    public void translate(float x, float y,float z);
    public void rotate(float angle,char axis);
    public void scale(float x,float y,float z);
    public void create(ObjectLoader loader,GL2 gl,float[] startPos);
    public void draw(GL2 gl);
    //public String getPath();
   // public void create(LoaderFactory factory, float[] startPos);
}
