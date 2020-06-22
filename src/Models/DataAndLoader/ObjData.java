package Models.DataAndLoader;

import com.jogamp.opengl.util.texture.Texture;
import javax.media.opengl.GL2;

public class ObjData {
    private Texture texture;
    private int list;
    private Material material;
    private float[] scale = {1,1,1};
    private float[] translate = {0,0,0};
    private float angleX;
    private float angleY;
    private float angleZ;
    private int textureWrap = GL2.GL_LINEAR;
    public ObjData() { }

    public void setMaterial(Material material) {
        this.material = material;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setList(int list){
        this.list = list;
    }
    public void setTextureWrap(int wrap){
        this.textureWrap = wrap;
    }

    public void setAngleX(float angleX) {
        this.angleX += angleX;
    }

    public void setAngleY(float angleY) {
        this.angleY += angleY;
    }

    public void setAngleZ(float angleZ) {
        this.angleZ += angleZ;
    }
    public void Scale(float[] change){
        if(change.length!= 3){
            System.out.println("change need to have x,y,z");
            return;
        }
        scale[0] *= change[0];
        scale[1] *= change[1];
        scale[2] *= change[2];
    }
    public void Translate(float[] change){
        if(change.length!= 3){
            System.out.println("change need to have x,y,z");
            return;
        }
        translate[0] += change[0];
        translate[1] += change[1];
        translate[2] += change[2];
    }


    public void draw(GL2 gl){
        gl.glPushMatrix();
        //gl.glLoadIdentity();
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, textureWrap);
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, textureWrap);


        gl.glTranslatef(translate[0],translate[1],translate[2]);
        gl.glScalef(scale[0],scale[1],scale[2]);

        gl.glRotatef(angleX,1,0,0);
        gl.glRotatef(angleY,0,1,0);
        gl.glRotatef(angleZ,0,0,1);
        if(texture != null)
            texture.bind(gl);
//TODO add material handler
        gl.glCallList(list);
        gl.glPopMatrix();

    }
}

