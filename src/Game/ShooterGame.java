package Game;/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import CollisionDetection.PointPolygonIntersection;
import Levels.GameLevels;
import Levels.Level;
import LinearMath.Vector;
import Models.Cube;
import Models.DataAndLoader.ObjData;
import Models.DataAndLoader.ObjectLoader;
import Models.PirateShip;
import Models.Weapons.Ak47;
import Models.Weapons.Cannon;
import Models.Weapons.Shotgun;
import Models.Weapons.Sword;
import Models.Wall;
import Models.IModel;
import Models.goods.Map;
import Models.goods.Treasure;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class ShooterGame extends KeyAdapter implements GLEventListener {
    private Texture cube;
    private Texture walls;
    private Texture topWall;
    private Texture bottomWall;
    private CoordinateSystem cooSystem;
    private static GLU glu;
    private static GLCanvas canvas;
    private static Frame frame;
    private static Animator animator;
    private PointPolygonIntersection ppi;
    private ObjectLoader loader = new ObjectLoader();
    private List<IModel> models = new ArrayList<>();
    private Character character;
    private PirateShip pirateShip;
    private Sword sword;
    private TextRenderer renderer;
    private GameLevels gameLevels;
    private Level level;
    private CoordinateSystem pirateShipCoor;
    private boolean startAnimation = true;
    public ShooterGame() {
        this.cooSystem =  new CoordinateSystem();
        this.pirateShipCoor = new CoordinateSystem();
        glu = new GLU();
        canvas = new GLCanvas();
        frame = new Frame("ThePirateShip");
        animator = new Animator(canvas);
        ppi = new PointPolygonIntersection();
        gameLevels = new GameLevels();
    }


    private void pirateShipAnimation(GL2 gl) {
        Vector origin = pirateShipCoor.getOrigin();
        Vector lookat = origin.minus(pirateShipCoor.getZ());
        Vector y = pirateShipCoor.getY();
        glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2),
                y.getVec()[0], y.getVec()[1], y.getVec()[2]);
        pirateShipCoor.moveStep('z', -0.04);
        pirateShipCoor.moveStep('y', -0.01);
        pirateShip.draw(gl);
        if (origin.get(2) < -90) {
            startAnimation = false;
        }
    }


    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        if (startAnimation) {
            pirateShipAnimation(gl);
        } else {
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_LINEAR);
            Vector origin = cooSystem.getOrigin();
            Vector lookat = origin.minus(cooSystem.getZ());
            lookat.normal();
            Vector y = cooSystem.getY();
            glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2),
                    y.getVec()[0], y.getVec()[1], y.getVec()[2]);
            gl.glPushMatrix();
            for (IModel model : models) {
                model.draw(gl);
            }
            gl.glPopMatrix();
            level.drawRooms();
            character.draw();
        }
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.3f, 1f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String filename="resources/Picture1.jpg"; // the FileName to open
            cube=TextureIO.newTexture(new File( filename ),true);
            //filename="resources/Picture2.jpg";
            filename="resources/woodenWall.jpg";
            walls=TextureIO.newTexture(new File( filename ),true);
            filename="resources/woodenWall.jpg";
            topWall=TextureIO.newTexture(new File( filename ),true);
            filename="resources/woodenWall.jpg";
            bottomWall=TextureIO.newTexture(new File( filename ),true);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glEnable(GL2.GL_LIGHTING);

        // Light
        float	ambient[] = {0.7f,0.7f,0.7f,1.0f};
        float	diffuse0[] = {0f,0f,0f,1.0f};
        float	diffuse1[] = {0f,0f,0f,1.0f};


        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse0, 0);
        gl.glEnable(GL2.GL_LIGHT0);

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse1, 0);
        gl.glEnable(GL2.GL_LIGHT1);

        gl.glEnable(GL2.GL_LIGHTING);

        sword = new Sword("objects/RzR/rzr.obj");
        float[] pos = {0f,5f,-10f};
        sword.create(loader,gl,pos);
//        models.add(sword);

        level = new Level(loader, gl, this);
        level.BuildLevel(gameLevels.getLevelsList().get(0));

//        Ak47 aK_471 = new Ak47("objects/AK_47/Ak-47.obj");
//        float[] akPos1 = {-4,4,-10};
//        aK_471.create(loader, gl,akPos1);
//        //aK_471.scale(0.01f,0.01f,0.01f);
//        models.add(aK_471);

//        Shotgun shotgun1 = new Shotgun("objects/Shotgun/GunTwo.obj");
//        float[] shotgunPos1 = {-4,4,-10};
//        shotgun1.create(loader, gl, shotgunPos1);
//        models.add(shotgun1);
//
//        //barrel
//        IModel barrel = new Barrel("objects/barrel/barrel_obj.obj");
//        float[] barrelPos = {10f,0f,-10f};
//        barrel.create(loader,gl,barrelPos);
//        models.add(barrel);

        //Bullet
//        IModel bullet = new Bullet("objects/Bullet/lowpolybullet.obj");
//        float[] bulletlPos = {10f,5f,-10f};
//        bullet.create(loader,gl,bulletlPos);
//        models.add(bullet);

        //Treasure
//        IModel treasure = new Treasure("objects/Treasure/treasure_chest.obj");
//        float[] trasurelPos = {10f,0f,-10f};
//        treasure.create(loader,gl,trasurelPos);
//        models.add(treasure);

        IModel map = new Map("objects/Map/14059_Pirate_Treasure_map_Scroll_v1_L1.obj");
        float[] mapPos = {-20.2f,5f,-10f};
        map.create(loader,gl,mapPos);
        map.rotate(90, 'y');
        models.add(map);

        //cannon
        Cannon cannon = new Cannon("objects/cannon/can.obj", level);
        float[] canPos = {0f,0f,-10f};
        cannon.create(loader,gl,canPos);
        cannon.rotate(270, 'x');
        models.add(cannon);

        //PirateShip
        pirateShip = new PirateShip("objects/PirateShip/boat.obj");
        float[] shipPos = {0,-30f,-100f};
        pirateShip.create(loader,gl,shipPos);
        pirateShip.scale(200,200,200);

        //models.add(ship);

        Ak47 AK_47 = new Ak47("objects/AK_47/Ak-47.obj", level);
        float[] akPos = {-10,3,8};
        AK_47.create(loader, gl,akPos);
        AK_47.translate(0.5f,-1.5f,0.2f);
        AK_47.scale(0.01f,0.01f,0.01f);
        AK_47.rotate(50,'x');
        AK_47.rotate(-70,'y');
        AK_47.rotate(45,'z');

        Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", level);
        float[] shotgunPos = {0,0,0};
        shotgun.create(loader, gl, shotgunPos);
        shotgun.translate(0.5f,-1f,-0.1f);
        shotgun.scale(7f,7f,7f);
        //shotgun.rotate(30,'x');
        shotgun.rotate(180,'y');
        shotgun.rotate(10,'z');
        //models.add(sword);
        this.character = new Character(shotgun,this.cooSystem,gl);
        character.AddWeapon(sword);
        character.AddWeapon(AK_47);
        character.setCurrentLevel(level);

        //create the room
        //models.addAll(createWalls(gl));
        Cube cube1 = new Cube(-20,0,-20,5);
        cube1.setTexture(cube);
        float[] cubePos = {0,0,0};
        cube1.create(loader,gl,cubePos);
        //models.add(cube1);
        Cube cube2 = new Cube(15,0,-20,5);
        cube2.setTexture(cube);
        cube2.create(loader,gl,cubePos);
        //models.add(cube2);


        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
        }
    }

    public void reshape(GLAutoDrawable drawable, int x,
                        int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0f, h, 1.0, 100.0);
        //gl.glOrtho(-6.0,6.0,-6.0,6.0,-6.0,6.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {

        int keyPressed = e.getKeyCode();
        if(keyPressed == KeyEvent.VK_SPACE){
            character.attack();
        }else if (keyPressed == KeyEvent.VK_Q) {
            character.changeWeapon();
        } else{
            character.walk(keyPressed);
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {

    }

    public static void exit(){
        animator.stop();
        frame.dispose();
        System.exit(0);
    }

    public void startGame() {
        canvas.addGLEventListener(this);
        frame.add(canvas);
        frame.setSize(800, 600);
//		frame.setUndecorated(true);
//		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub

    }

    public void displayChanged(GLAutoDrawable gLDrawable,
                               boolean modeChanged, boolean deviceChanged) {
    }

    public ArrayList<IModel> createWalls(GL2 gl){
        ArrayList<IModel> models = new ArrayList<>();
        float[] wallPos = {0,0,0};
        Wall front = new Wall(-20,0,-20,'x',10,40);
        front.setTex(walls);
        front.create(loader,gl,wallPos);
        models.add(front);


        Wall back = new Wall(-20.0f,0.0f,20.0f,'x',10,40);
        back.setTex(walls);
        back.create(loader,gl,wallPos);
        models.add(back);


        Wall right = new Wall(20.0f,0.0f,-20.0f,'z',10,40);
        right.setTex(walls);
        right.create(loader,gl,wallPos);
        models.add(right);


        Wall left = new Wall(-20.0f,0.0f,-20.0f,'z',10,40);
        left.setTex(walls);
        left.create(loader,gl,wallPos);
        models.add(left);


        Wall top = new Wall(-20.0f,10.0f,-20.0f,'y',40,40);
        top.setTex(topWall);
        top.create(loader,gl,wallPos);
        models.add(top);


        Wall bottom= new Wall(-20.0f,0.0f,-20.0f,'y',40,40);
        bottom.setTex(bottomWall);
        bottom.create(loader,gl,wallPos);
        models.add(bottom);

        return models;
    }

}