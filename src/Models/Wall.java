/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models;

import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL2;

public class Wall implements Model {
    private float x,y,z;
    private char axis;
    private float width,length;
    private Texture tex;
    private float color[];
    public Wall(float x,float y,float z,char axis,float width,float length){
        this.x = x;
        this.y = y;
        this.z = z;
        this.axis = axis;
        this.width = width;
        this.length = length;
    }

    public void setTex(Texture tex) {
        this.tex = tex;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setColor(float red, float green, float blue){
        this.color = new float[3];
        this.color[0] = red;
        this.color[1] = green;
        this.color[2] = blue;
    }
    @Override
    public void draw(GL2 gl){
        tex.bind(gl);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        float texwidth = length/5.f;
        float texhieght = width/5.f;
        gl.glBegin(GL2.GL_QUADS);
        if(color != null){
            gl.glColor3f(color[0],color[1],color[2]);
        }

        //texture2.bind(gl);
        gl.glTexCoord2f(0.0f,0.0f);
        gl.glVertex3f(x,y,z);
        gl.glTexCoord2f(0f,texhieght);
        switch(axis){
            case 'y':
                z+=width;
                break;
            default:
                y+=width;
        }
        gl.glVertex3f(x,y,z);
        switch(axis){
            case 'z':
                z+=length;
                break;
            default:
                x+=length;
        }
        gl.glTexCoord2f(texwidth,texhieght);
        gl.glVertex3f(x,y,z);
        switch(axis){
            case 'y':
                z-=width;
                break;
            default:
                y-=width;
        }
        gl.glTexCoord2f(texwidth,0f);
        gl.glVertex3f(x,y,z);
        gl.glEnd();
    }

}
