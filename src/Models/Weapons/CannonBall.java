package Models.Weapons;

import CollisionDetection.BoundingSphere;
import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.Model;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class CannonBall extends Model {
    private String path;
    private float move = 0;
    private long startTime = System.currentTimeMillis();
    private float rot = 0;
    private float scaleFactor = 1;
    private float[] posAfterRot = {0,0,0};
    private CollisionData collisionData;
    private boolean dead = false;
    public CannonBall(String path) {
        this.path = path;
    }

    public CannonBall() {
    }
    public CannonBall(String path, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path,CollisionType.BS);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        //this.translate(1f,0f,0f);
    }
    public void setStartPos(float[] startPos){
        this.startPos = startPos;
        this.collisionData.setStartPos(startPos);
    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos) {
        data = loader.LoadModelToGL(path,gl, CollisionType.BS);
        this.collisionData = loader.getCollisionData();
        this.translate(1f,0f,0f);
        this.startPos = startPos;
        //this.collisionData.setStartPos(startPos);
    }
    public CannonBall create(float[] startPos){
        CannonBall ball = new CannonBall();
        ball.data = this.data;
        ball.startTime = System.currentTimeMillis();
        BoundingSphere bs = (BoundingSphere)this.collisionData;
        ball.collisionData = new BoundingSphere(bs.center,bs.radius);
        ball.collisionData.setStartPos(startPos);
        //ball.translate(1f,0f,0f);
        ball.startPos = startPos;


        return ball;

    }
    public void setScaleFactor(float sf){
        this.scaleFactor = sf;
        //float[] scale = {sf*4,sf*4,sf*4};
        //this.collisionData.setScale(scale);
    }
    private void destroy(){
        this.dead = true;
    }
    @Override
    public void draw(GL2 gl) {
        if(!dead) {


            gl.glPushMatrix();

            //calculate fps
            long endTime = System.currentTimeMillis();
            long timePassed = endTime - startTime;
            move -= 0.02f * (float) timePassed;
            //move -= 10f;
            startTime = System.currentTimeMillis();
            if (Math.abs(move) > 200) {
                this.destroy();
            }
            //update collision data
            this.collisionData.init();
            float[] moveArr = {0, 0, move};
            float[] scale = {this.scaleFactor * 2, this.scaleFactor * 2, this.scaleFactor * 2};
            float[] trans = {2, 0, 0};

            this.collisionData.transAfterRotate(posAfterRot);
            this.collisionData.transAfterRotate(moveArr);
            this.collisionData.setScale(scale);
            this.collisionData.transAfterRotate(trans);
            this.collisionData.draw(gl);


            gl.glTranslatef(startPos[0], startPos[1], startPos[2]);
            gl.glRotatef(270 + rot, 0, 1, 0);
            gl.glTranslatef(posAfterRot[0], posAfterRot[1], move + posAfterRot[2]);
            gl.glScalef(4f, 4f, 4f);
            gl.glScalef(scaleFactor, scaleFactor, scaleFactor);

            gl.glTranslatef(1f,0f,0f);
            for (ObjData obj : data) {
                obj.draw(gl);
            }
            gl.glPopMatrix();
        }

    }
    public void setRot(float angle){
        rot = angle;
        float[] rotate = {0,270+rot,0};
        this.collisionData.setRotate(rotate);
    }

    public void setPosAfterRot(float[] posAfterRot) {
        this.posAfterRot = posAfterRot;
    }
}
