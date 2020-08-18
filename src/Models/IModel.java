/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import Models.DataAndLoader.ObjectLoader;
import javax.media.opengl.GL2;

public interface IModel {
    void translate(float x, float y, float z);
    void rotate(float angle, char axis);
    void scale(float x, float y, float z);
    void draw(GL2 gl);
}
