package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.ICollisionObj;
import Game.CoordinateSystem;
import Levels.Level;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import javax.media.opengl.GL2;

public class Shotgun extends Weapon implements ICollisionObj {
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

    public Shotgun(String inPath, Level level) {
        this.path = inPath;
        weapontype = WeaponType.GUN;
        this.observer = level;
        targetSymbol = new TargetSymbol("objects/TargetSymbol/TargetSymbol.obj");
    }

    public void create(ObjectLoader loader, GL2 gl,float[] startPos){
        data = loader.LoadModelToGL(path,gl,"AABB");
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

    private void addAsRoomModel(Bullet bullet) {
        bullet.setAngle(angle);
        observer.addModel(bullet);
    }

    @Override
    public void draw(GL2 gl) {
        gl.glPushMatrix();
        if(!picked){
            this.collisionData.draw(gl);
            gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
            gl.glScalef(5f, 5f, 5f);
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
                addAsRoomModel(magazine.shotBullet(pos1,bulletPos1));
                addAsRoomModel(magazine.shotBullet(pos2,bulletPos2));
            }
            rChange -= (5f/duration)*milliseconds;
            gl.glRotatef(rChange,1,0,0);
        }
    }

    @Override
    public void collide() {

    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }
}
