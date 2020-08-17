package Models.Weapons;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import CollisionDetection.ICollisionObj;
import Game.CoordinateSystem;
import Levels.Level;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import SoundEffects.SoundEffect;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.List;

public class Ak47 extends Weapon {
    //private String path;
    private Magazine magazine;
    private String bulletPath;
    private boolean attackMode = false;
    private float duration = 250f;
    private float time;
    private float change = 0;
    private float rChange = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long milliseconds;
    private boolean shot = false;
    private CoordinateSystem cooSystem;
    private float angle;
    private TargetSymbol targetSymbol;
    private CollisionData collisionData;
    public Ak47(String inPath, Level level) {
        this.level = level;
        this.path = inPath;
        weapontype = WeaponType.GUN;
        targetSymbol = new TargetSymbol("objects/TargetSymbol/TargetSymbol.obj");
    }
    public Ak47(String path,Level level, LoaderFactory factory){
        Pair<List<ObjData>,CollisionData> data = factory.create(path, CollisionType.AABB);
        this.data = data.getKey();
        this.collisionData = data.getValue();
        this.level = level;
        this.weapontype = WeaponType.GUN;
        this.magazine = new Magazine(factory,20);
        this.bulletPath = "objects/Bullet/lowpolybullet.obj";
        this.targetSymbol = new TargetSymbol("objects/TargetSymbol/TargetSymbol.obj",factory);
        //this.targetSymbol.scale(2,2,2);
        this.targetSymbol.rotate(90,'x');
        //this.scale(0.01f,0.01f,0.01f);
        float[] scale = {0.01f,0.01f,0.01f};
        this.collisionData.setScale(scale);
        this.sound = new SoundEffect();
    }

    public void setCoordinateSystem(CoordinateSystem cooSystem) {
        this.cooSystem = cooSystem;
    }

    public void setStartPos(float[] startPos){
        //pos of weapon
        this.startPos = startPos;
        //pos of target
        float[] targerpos = {startPos[0] + 10,startPos[1] - 3.4f, startPos[2] - 15};
        this.targetSymbol.setStartPos(targerpos);

        //pos of collisionData

        this.collisionData.setStartPos(startPos);

    }
    //old
    @Override
    public void create(ObjectLoader loader, GL2 gl, float[] startPos){
        data = loader.LoadModelToGL(path,gl,CollisionType.AABB);
        this.collisionData = loader.getCollisionData();

        this.startPos = startPos;
        this.magazine = new Magazine(loader, gl, 20);
        float[] targerpos = {startPos[0] + 10,startPos[1] - 3.4f, startPos[2] - 15};
        this.targetSymbol.create(loader,gl, targerpos);
        targetSymbol.scale(2, 2, 2);
        targetSymbol.rotate(90, 'x');

        this.collisionData.setStartPos(startPos);
        float[] scale = {0.01f,0.01f,0.01f};
        this.collisionData.setScale(scale);
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
            this.collisionData.draw(gl);
            gl.glTranslatef(startPos[0],startPos[1],startPos[2]);
            gl.glScalef(0.01f, 0.01f, 0.01f);
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
        //drawTarget(startPos[0], startPos[1]);
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
        sound.play("resources/SoundEffects/shotgun_reload.wav");
        return magazine.reload();
    }

    @Override
    public void setAngle(float angle) {
        this.angle = angle;
    }

    private void moveGun(GL2 gl) {
        if(time <= duration/2){
            rChange -= (5f/duration)*milliseconds;
            gl.glRotatef(rChange,0,1,0);
        }else{
            if (!shot) {
                shot = true;
                float[] pos1 = {(float) cooSystem.getOrigin().getVec()[0], (float) cooSystem.getOrigin().getVec()[1]
                        , (float) cooSystem.getOrigin().getVec()[2]};
                float[] pos2 = {0f,-0.48f,-3.9f};
                int roomNum = this.level.getRoom(pos1);
                addAsRoomModel(magazine.shotBullet(pos1,pos2,bulletPath,this.level),roomNum);
                sound.play("resources/SoundEffects/ak47shot.mp3");
            }
            rChange += (5f/duration)*milliseconds;
            gl.glRotatef(rChange,0,1,0);

        }
        // y = -0.48f ,z = -3.9
    }

    @Override
    public void collide(ICollisionObj obj) {

    }

    @Override
    public CollisionData getCollisionData() {
        return this.collisionData;
    }
    @Override
    public void weaponPicked() {
        if(!this.picked){
            sound.play("resources/SoundEffects/weaponPickUp.wav");
            this.level.removeModel(this);

            this.picked = true;

            float[] akPos = {-10,3,8};
            this.setStartPos(akPos);
            this.translate(0.5f,-1.5f,0.2f);
            this.scale(0.01f,0.01f,0.01f);
            this.rotate(50,'x');
            this.rotate(-70,'y');
            this.rotate(45,'z');
        }
    }
}
