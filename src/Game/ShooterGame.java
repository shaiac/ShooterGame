package Game;/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import CollisionDetection.PointPolygonIntersection;
import Levels.GameLevels;
import Levels.Level;
import LinearMath.Vector;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjectLoader;
import Models.Enemys.Enemy;
import Models.PirateShip;
import Models.Weapons.Ak47;
import Models.Weapons.Shotgun;
import Models.Weapons.Sword;
import Models.IModel;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.newt.event.awt.AWTMouseAdapter;
import com.jogamp.opengl.util.Animator;

public class ShooterGame extends KeyAdapter implements GLEventListener, MouseListener, MouseMotionListener {
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
    private GameLevels gameLevels;
    private Level level;
    private double[] mousePos = {0,0,1};
    private boolean attack = false;
    private List<Enemy> enemies;
    private StartAnimation startAnimation;
    private int levelNum = 0;
    private boolean nextLevel = false;
    private LoaderFactory factory;

    public ShooterGame() {
        this.cooSystem =  new CoordinateSystem();
        glu = new GLU();
        canvas = new GLCanvas();
        frame = new Frame("ThePirateShip");
        animator = new Animator(canvas);
        ppi = new PointPolygonIntersection();
        gameLevels = new GameLevels();
    }

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        if (startAnimation.toStop()) {
            if(nextLevel){
                level = new Level(loader, gl, this);
                level.BuildLevel(gameLevels.getLevelsList().get(levelNum));
                nextLevel = false;
            }
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_LINEAR);
            Vector origin = cooSystem.getOrigin();
            Vector lookat = origin.minus(cooSystem.getZ());
            lookat.normal();
            Vector y = cooSystem.getY();
            glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2),
                    y.getVec()[0], y.getVec()[1], y.getVec()[2]);
//            gl.glPushMatrix();
//            for (IModel model : models) {
//                model.draw(gl);
//            }
//            gl.glPopMatrix();
            level.updatePos(origin);
            level.updateRooms();
            level.drawRooms();
            //level.drawEnemies();
            character.draw();
            //attack until release left button
            if (attack) {
                character.attack();
            }
        } else {
            startAnimation.pirateShipAnimation(glu);
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
        //end of lightning

        this.factory = new LoaderFactory(this.loader,gl);
        level = new Level(this.factory,this,loader,gl);
        level.BuildLevel(gameLevels.getLevelsList().get(levelNum));

        startAnimation = new StartAnimation(gl, loader);

        //character starting weapons

        Ak47 AK_47 = new Ak47("objects/AK_47/Ak-47.obj", level,this.factory);
        float[] akPos = {-10,3,8};
        //AK_47.create(loader, gl,akPos);
        AK_47.setStartPos(akPos);
        AK_47.translate(0.5f,-1.5f,0.2f);
        AK_47.scale(0.01f,0.01f,0.01f);
        AK_47.rotate(50,'x');
        AK_47.rotate(-70,'y');
        AK_47.rotate(45,'z');

        Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", level,this.factory);
        float[] shotgunPos = {0,0,0};
        //shotgun.create(loader, gl, shotgunPos);
        shotgun.setStartPos(shotgunPos);
        shotgun.translate(0.5f,-1f,-0.1f);
        shotgun.scale(7f,7f,7f);
        shotgun.rotate(180,'y');
        shotgun.rotate(10,'z');

        sword = new Sword("objects/RzR/rzr.obj");
        float[] pos = {0f,5f,-10f};
        sword.create(loader,gl,pos);

        this.character = new Character(shotgun,this.cooSystem,gl);
        character.AddWeapon(sword);
        character.AddWeapon(AK_47);
        character.setCurrentLevel(level);

        if (drawable instanceof Window) {
            Window window = (Window) drawable;
            window.addKeyListener(this);
            window.addMouseListener(this);
        } else if (GLProfile.isAWTAvailable() && drawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) drawable;
            new AWTKeyAdapter(this, drawable).addTo(comp);
            new AWTMouseAdapter(this,drawable).addTo(comp);
            character.setCurrentLevel(level);
        }
    }

    public void currentLevelEnded(GL2 gl) {
        levelNum++;
        this.nextLevel = true;

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
        glu.gluPerspective(80.0f, h, 1.0, 160.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {

        int keyPressed = e.getKeyCode();
        if(keyPressed == KeyEvent.VK_SPACE){
            character.attack();
        }
        if (keyPressed == KeyEvent.VK_Q) {
            character.changeWeapon();
        }
        /*if (keyPressed == KeyEvent.VK_CONTROL) {
            level.getTmpCannon().fire();
        }*/
        else{
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

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        mousePos[0] = mouseEvent.getX();
        mousePos[1] = mouseEvent.getY();
        if(mousePos[0] > frame.getWidth()){
            mousePos[0] = frame.getWidth();
        }
        if(mousePos[1] > frame.getHeight()){
            mousePos[1] = frame.getHeight();
        }

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

        mousePos[0] = mouseEvent.getX();
        mousePos[1] = mouseEvent.getY();
        if(mousePos[0] > frame.getWidth()){
            mousePos[0] = frame.getWidth();
        }
        if(mousePos[1] > frame.getHeight()){
            mousePos[1] = frame.getHeight();
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            this.attack = true;
        }
        if(mouseEvent.getButton() == MouseEvent.BUTTON3){
            character.reload();
        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            this.attack = false;
        }
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        double[] mPos = {mouseEvent.getX(), mouseEvent.getY(),1};
        if(mPos[0] > frame.getWidth()){
            mPos[0] = frame.getWidth();
        }
        if(mPos[1] > frame.getHeight()){
            mPos[1] = frame.getHeight();
        }
        if(mPos[0] < 0){
            mPos[0] = 0;
        }
        if(mPos[1] < 0){
            mPos[1] = 0;
        }
        double angle = Math.PI*4/(double) frame.getWidth();
        double diff = mPos[0] - mousePos[0];
        character.rotate('y',diff*angle);
        mousePos = mPos;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        double[] mPos = {mouseEvent.getX(), mouseEvent.getY(),1};
        if(mPos[0] > frame.getWidth()){
            mPos[0] = frame.getWidth();
        }
        if(mPos[1] > frame.getHeight()){
            mPos[1] = frame.getHeight();
        }
        if(mPos[0] < 0){
            mPos[0] = 0;
        }
        if(mPos[1] < 0){
            mPos[1] = 0;
        }
        double angle = Math.PI*4/(double) frame.getWidth();
        double diff = mPos[0] - mousePos[0];
        character.rotate('y',diff*angle);
        mousePos = mPos;
    }

    @Override
    public void mouseWheelMoved(MouseEvent mouseEvent) {
        character.changeWeapon();
    }

    /**
     * Invoked when a mouse button is pressed on a component and then
     * dragged.  <code>MOUSE_DRAGGED</code> events will continue to be
     * delivered to the component where the drag originated until the
     * mouse button is released (regardless of whether the mouse position
     * is within the bounds of the component).
     * <p>
     * Due to platform-dependent Drag&amp;Drop implementations,
     * <code>MOUSE_DRAGGED</code> events may not be delivered during a native
     * Drag&amp;Drop operation.
     *
     * @param e
     */
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {

    }

    /**
     * Invoked when the mouse cursor has been moved onto a component
     * but no buttons have been pushed.
     *
     * @param e
     */
    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {

    }

}