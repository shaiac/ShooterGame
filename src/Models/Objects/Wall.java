/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Objects;

import CollisionDetection.AABB;
import CollisionDetection.CollisionData;
import CollisionDetection.CollisionPolygon;
import CollisionDetection.ICollisionObj;
import LinearMath.Vector;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import com.jogamp.opengl.util.texture.Texture;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Wall extends Model implements IObstacle {
    private float x,y,z;
    private char axis;
    private float width,length;
    private Texture tex;
    private float color[];
    private int list;
    private ObjData data = new ObjData();
    private List<Vector> rectangle;
    private CollisionData collisionData;
    private float[] normal;
    public Wall(float x,float y,float z,char axis,float width,float length){
        this.x = x;
        this.y = y;
        this.z = z;
        this.axis = axis;
        this.width = width;
        this.length = length;
        this.rectangle = new ArrayList<>();
        insertVertex(x,y,z);
    }

    public void setTex(Texture tex) {
        data.setTexture(tex);
        data.setTextureWrap(GL2.GL_REPEAT);
    }

    public void setColor(float red, float green, float blue){
        this.color = new float[3];
        this.color[0] = red;
        this.color[1] = green;
        this.color[2] = blue;
    }
    public void create(ObjectLoader loader, GL2 gl, float[] pos){
        float texwidth = length/40.f;
        float texhieght = width/40.f;
        float[] normal = getNormal();
        texhieght = 1;
        texwidth = 1;
        list = gl.glGenLists(1);
        gl.glNewList(list,GL2.GL_COMPILE);

        gl.glBegin(GL2.GL_QUADS);
        if(color != null){
            gl.glColor3f(color[0],color[1],color[2]);
        }
        gl.glNormal3fv(normal,0);
        gl.glTexCoord2f(0.0f,0.0f);
        gl.glVertex3f(x,y,z);
        double[] min = {x,y,z,1};
        double[] max = {x,y,z,1};


        switch(axis){
            case 'y':
                z+=width;
                break;
            default:
                y+=width;
        }
        gl.glNormal3fv(normal,0);
        gl.glTexCoord2f(0f,texhieght);
        gl.glVertex3f(x,y,z);

        if(x<min[0]){
            min[0] = x;
        }else if(x> max[0]){
            max[0] = x;
        }
        if(y<min[1]){
            min[1] = y;
        }else if(y> max[1]){
            max[1] = y;
        }
        if(z<min[2]){
            min[2] = z;
        }else if(z> max[2]){
            max[2] = z;
        }


        switch(axis){
            case 'z':
                z+=length;
                break;
            default:
                x+=length;
        }
        gl.glNormal3fv(normal,0);
        gl.glTexCoord2f(texwidth,texhieght);
        gl.glVertex3f(x,y,z);

        if(x<min[0]){
            min[0] = x;
        }else if(x> max[0]){
            max[0] = x;
        }
        if(y<min[1]){
            min[1] = y;
        }else if(y> max[1]){
            max[1] = y;
        }
        if(z<min[2]){
            min[2] = z;
        }else if(z> max[2]){
            max[2] = z;
        }


        switch(axis){
            case 'y':
                z-=width;
                break;
            default:
                y-=width;
        }
        gl.glNormal3fv(normal,0);
        gl.glTexCoord2f(texwidth,0f);
        gl.glVertex3f(x,y,z);

        if(x<min[0]){
            min[0] = x;
        }else if(x> max[0]){
            max[0] = x;
        }
        if(y<min[1]){
            min[1] = y;
        }else if(y> max[1]){
            max[1] = y;
        }
        if(z<min[2]){
            min[2] = z;
        }else if(z> max[2]){
            max[2] = z;
        }

        gl.glEnd();
        gl.glEndList();
        data.setList(list);
        if(axis == 'x'){
            min[2] -= 0.5;
            max[2] += 0.5;
            min[0] += 0.5;
            max[0] -= 0.5;
        }else if(axis == 'z'){
            min[0] -= 0.5;
            max[0] += 0.5;
            min[2] += 0.5;
            max[2] -= 0.5;
        }
        Vector minVec = new Vector(min,4);
        Vector maxVec = new Vector(max,4);
        this.collisionData = new AABB(minVec,maxVec);
        //this.collisionData = new CollisionPolygon(rect);
    }
    @Override
    public void draw(GL2 gl){
        float[] color = {0.8f,0.8f,0.8f,1};
        float[] ambient = {1,1,1,1};
        float[] spec = {1,1,1,1};
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_DIFFUSE,color,0);
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT,ambient,0);
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,spec,0);
        //gl.glFrontFace(GL.GL_CW);
        //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_S);
        this.collisionData.draw(gl);
        data.draw(gl);
    }


    private void insertVertex(double vx, double vy, double vz) {
        double[] arrVec = {vx, vy, vz};
        Vector vec1 = new Vector(arrVec, 3);
        rectangle.add(0, vec1);
    }

    private void createRectangle() {

        double[] arrVec1 = {x, y, z,1}; //top left
        Vector vec1 = new Vector(arrVec1, 4);
        rectangle.add(vec1);
        double[] arrVec2 = {20, 40, -20};//top right
        Vector vec2 = new Vector(arrVec2, 3);
        rectangle.add(1, vec2);
        double[] arrVec3 = {20, 0, -20};//bottom right
        Vector vec3 = new Vector(arrVec3, 3);
        rectangle.add(2, vec3);
        double[] arrVec4 = {x, y, z}; //bottom left
        Vector vec4 = new Vector(arrVec4, 3);
        rectangle.add(3, vec4);
    }

    public List<Vector> getRectangle() {
        return rectangle;
    }

    @Override
    public void collide(ICollisionObj obj) {

    }

    private float[] getNormal(){
        float[] normal = {0,0,0};
        if(axis == 'x'){
            if(length > 0){
                normal[2] = -1;
            } else {
                normal[2] = 1;
            }
        } else if(axis == 'z'){
            if(length > 0){
                normal[0] = -1;
            }else{
                normal[0] = 1;
            }
        } else {
            if(this.y == 0){
                normal[1] = 1;
            }else{
                normal[1] = -1;
            }
        }
        return normal;
    }
    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }
}
