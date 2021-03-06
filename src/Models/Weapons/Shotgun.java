/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.CoordinateSystem;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Shotgun extends Weapon implements ICollisionObj  {
    private String path;
    private Magazine magazine;
    private boolean attackMode = false;
    private float duration = 500f;
    private float time;
    private float change = 0;
    private float rChange = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long milliseconds;
    private boolean shot = false;
    private float angle;
    private TargetSymbol targetSymbol;
    private CoordinateSystem cooSystem;
    private CollisionData collisionData;
    private LoaderFactory factory;
    private String bulletPath;

    public Shotgun(String path, Level level, LoaderFactory factory){
        this.factory = factory;
        Pair<List<ObjData>,CollisionData> data = factory.create(path,CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.level = level;
        this.weapontype = WeaponType.GUN;
        this.magazine = new Magazine(factory,8);
        this.bulletPath = "objects/Bullet/lowpolybullet.obj";
        this.targetSymbol = new TargetSymbol("objects/TargetSymbol/TargetSymbol.obj",factory);
        this.targetSymbol.scale(2,2,2);
        this.targetSymbol.rotate(90,'x');

        float[] scale = {7f,7f,7f};
        this.collisionData.setScale(scale);
        float[] rotate = {0,90f,0};
        this.collisionData.setRotate(rotate);
    }

    public void setStartPos(float[] startPos){
        //pos of weapon
        this.startPos = startPos;
        //pos of target
        float[] targerpos = {startPos[0] + 0.4f,startPos[1] - 0.4f, startPos[2] - 10};
        this.targetSymbol.setStartPos(targerpos);

        //pos of collisionData
        this.collisionData.setStartPos(startPos);

    }
    public void create(ObjectLoader loader, GL2 gl,float[] startPos){
        data = loader.LoadModelToGL(path,gl, CollisionType.AABB);
        this.collisionData = loader.getCollisionData();
        this.startPos = startPos;
        this.magazine = new Magazine(loader, gl, 8);
        float[] targerpos = {startPos[0] + 0.4f,startPos[1] - 0.4f, startPos[2] - 10};
        this.targetSymbol.create(loader,gl, targerpos);
        targetSymbol.scale(2, 2, 2);
        targetSymbol.rotate(90, 'x');

        this.collisionData.setStartPos(startPos);
        float[] scale = {10f,10f,10f};
        this.collisionData.setScale(scale);
        float[] rotate = {0,90f,0};
        this.collisionData.setRotate(rotate);
    }

    private void addAsRoomModel(Bullet bullet,int roomNum) {
        bullet.setAngle(angle);
        bullet.setRoomNum(roomNum);
        level.addModel(bullet,roomNum);
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        if(!picked){
            gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
            gl.glScalef(7f, 7f, 7f);
            gl.glRotatef(90f, 0f, 1f, 0f);
            drawUnpicked(gl);
        }
        if(attackMode){
            endTime = System.currentTimeMillis();
            milliseconds = endTime- startTime;
            change = (1f/duration)*milliseconds;

            for (ObjData obj:data) {
                moveGun(gl);
            }
            time += milliseconds;
            if(time >= duration){
                shot = false;
                attackMode = false;
                time = 0;
                rChange = 0f;
            }
            startTime = endTime;
        }
        for (ObjData obj:data) {
            obj.draw(gl);
        }
        gl.glPopMatrix();
        if (picked) {
            targetSymbol.draw(gl);
        }
    }

    @Override
    public void attack() {
        if (!magazine.isEmpty()) {
            attackMode = true;
            startTime = System.currentTimeMillis();

        }
    }

    @Override
    public int reload() {
        sound.play("SoundEffects/shotgun_reload.wav");
        return magazine.reload();
    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }
    @Override
    public void setCoordinateSystem(CoordinateSystem coordinateSystem) {
        this.cooSystem = coordinateSystem;
    }

    private void moveGun(GL2 gl) {
        if(time <= duration/2){
            rChange += (5f/duration)*milliseconds;
            gl.glRotatef(rChange,1,0,0);
            //gl.glTranslatef(0.5f,0.5f,-0.5f);
        }else{
            if (!shot) {
                shot = true;
                float[] pos1 = {(float) cooSystem.getOrigin().getVec()[0], (float) cooSystem.getOrigin().getVec()[1]
                        , (float) cooSystem.getOrigin().getVec()[2]};
                float[] bulletPos1 = {0.5f,-0.38f,-3.9f};
                float[] pos2 = {(float) cooSystem.getOrigin().getVec()[0], (float) cooSystem.getOrigin().getVec()[1]
                        , (float) cooSystem.getOrigin().getVec()[2]};

                float[] bulletPos2 = {0.6f,-0.38f,-3.9f};
                int roomNum = this.level.getRoom(pos1);
                addAsRoomModel(magazine.shotBullet(pos1,bulletPos1,bulletPath,this.level),roomNum);
                addAsRoomModel(magazine.shotBullet(pos2,bulletPos2,bulletPath,this.level),roomNum);
                sound.play("SoundEffects/shotgun-shot.wav");
            }
            rChange -= (5f/duration)*milliseconds;
            gl.glRotatef(rChange,1,0,0);
        }
    }

    @Override
    public void collide(ICollisionObj obj) {

    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }

    @Override
    public void weaponPicked(){
        if(!this.picked) {
            sound.play("SoundEffects/weaponPickUp.wav");
            this.level.removeModel(this);
            this.picked = true;

            float[] shotgunPos = {0, 0, 0};
            this.setStartPos(shotgunPos);
            this.translate(0.5f, -1f, -0.1f);
            this.scale(7f, 7f, 7f);
            this.rotate(180, 'y');
            this.rotate(10, 'z');
        }
    }
}
