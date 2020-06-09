/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;

import LinearMath.Vector;
import Models.Cube;
import Models.Wall;
import Models.Model;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyAdapter;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.Animator;
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

    public ShooterGame() {
        this.cooSystem =  new CoordinateSystem();
        glu = new GLU();
        canvas = new GLCanvas();
        frame = new Frame("Moving Game");
        animator = new Animator(canvas);
    }

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        float position0[] = {20f,10f,0f,1.0f};		// red light on the right side (light 0)
        float position1[] = {-20f,10f,0f,1.0f};	// blue light on the left side (light 1)
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
        gl.glTexParameteri ( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_S, GL2.GL_LINEAR);
        gl.glTexParameteri( GL2.GL_TEXTURE_2D,GL2.GL_TEXTURE_WRAP_T, GL2.GL_LINEAR);
        Vector origin = cooSystem.getOrigin();
        Vector lookat = origin.minus(cooSystem.getZ());
        lookat.normal();
        Vector y = cooSystem.getY();

        //The light
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position0, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, position1, 0);

        glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2), y.getVec()[0], y.getVec()[1], y.getVec()[2]);

        // create the model
        ArrayList<Model> models = new ArrayList<>();
        models.addAll(createWalls());
        Cube cube1 = new Cube(-20,0,-20,5);
        cube1.setTexture(cube);
        models.add(cube1);
        Cube cube2 = new Cube(15,0,-20,5);
        cube2.setTexture(cube);
        models.add(cube2);

        for (Model model:models
             ) {
            model.draw(gl);
        }
    }

    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        try {
            String filename="resources/Picture1.jpg"; // the FileName to open
            cube=TextureIO.newTexture(new File( filename ),true);
            filename="resources/Picture2.jpg";
            walls=TextureIO.newTexture(new File( filename ),true);
            filename="resources/TopWall.jpg";
            topWall=TextureIO.newTexture(new File( filename ),true);
            filename="resources/BottomWall.jpg";
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
        float	diffuse0[] = {1f,0f,0f,1.0f};
        float	diffuse1[] = {0f,0f,1f,1.0f};


        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse0, 0);
        gl.glEnable(GL2.GL_LIGHT0);

        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse1, 0);
        gl.glEnable(GL2.GL_LIGHT1);

        gl.glEnable(GL2.GL_LIGHTING);

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
        glu.gluPerspective(100.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        float step = 1.0f;
        double angle = 0.05;
        char keyPressed = e.getKeyChar();
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            exit();
        } else if (keyPressed == 'i' || keyPressed == 'I') {
            cooSystem.rotate('x', angle);
        } else if (keyPressed == 'k' || keyPressed == 'K') {
            cooSystem.rotate('x', -angle);
        } else if (keyPressed == 'l' || keyPressed == 'L') {
            cooSystem.rotate('y', angle);
        } else if (keyPressed == 'j' || keyPressed == 'J') {
            cooSystem.rotate('y', -angle);
        } else if (keyPressed == 'o' || keyPressed == 'O') {
            cooSystem.rotate('z', -angle);
        } else if (keyPressed == 'u' || keyPressed == 'U') {
            cooSystem.rotate('z', angle);
        }
        else if (e.getKeyCode() == KeyEvent.VK_W) {
            cooSystem.moveStep('z', -step);
        } else if (keyPressed == 's' || keyPressed == 'S') {
            cooSystem.moveStep('z', step);
        } else if (keyPressed == 'd' || keyPressed == 'D') {
            cooSystem.moveStep('x', step);
        } else if (keyPressed == 'a' || keyPressed == 'A') {
            cooSystem.moveStep('x', -step);
        } else if (keyPressed == 'e' || keyPressed == 'E') {
            cooSystem.moveStep('y', step);
        } else if (keyPressed == 'q' || keyPressed == 'Q') {
            cooSystem.moveStep('y', -step);
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

    public ArrayList<Model> createWalls(){
        ArrayList<Model> models = new ArrayList<>();

        Wall front = new Wall(-20,0,-20,'x',10,40);
        front.setTex(walls);
        models.add(front);


        Wall back = new Wall(-20.0f,0.0f,20.0f,'x',10,40);
        back.setTex(walls);
        models.add(back);


        Wall right = new Wall(20.0f,0.0f,-20.0f,'z',10,40);
        right.setTex(walls);
        models.add(right);


        Wall left = new Wall(-20.0f,0.0f,-20.0f,'z',10,40);
        left.setTex(walls);
        models.add(left);


        Wall top = new Wall(-20.0f,10.0f,-20.0f,'y',40,40);
        top.setTex(topWall);
        models.add(top);


        Wall bottom= new Wall(-20.0f,0.0f,-20.0f,'y',40,40);
        bottom.setTex(bottomWall);
        models.add(bottom);

        return models;
    }


}