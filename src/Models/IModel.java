/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import javax.media.opengl.GL2;

public interface IModel {
    public void translate(float x, float y,float z);
    public void rotate(float angle,char axis);
    public void scale(float x,float y,float z);
    public void draw(GL2 gl);
}