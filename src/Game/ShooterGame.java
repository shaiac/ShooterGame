package Game;/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import Levels.GameLevels;
import Levels.Level;
import LinearMath.Vector;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjectLoader;
import Models.Weapons.Sword;
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
    private ObjectLoader loader = new ObjectLoader();
    private Character character;
    private Sword sword;
    private GameLevels gameLevels;
    private Level level;
    private double[] mousePos = {0,0,1};
    private boolean attack = false;
    private StartAnimation startAnimation;
    private int levelNum = 0;
    private boolean nextLevel = false;
    private LoaderFactory factory;
    private GameOver gameOver;
    private Help help;
    private boolean needHelp = false;
    public int playerDecision = -1;
    private boolean firstInit = true;
    private StartingMenu startingMenu;
    private boolean starting = true;
    private double[] movement = {0,0,0};
    private double[] rotate = {0,0,0};
    private boolean reload = false;
    private FPS fps = new FPS();
    private long startTime;
    private LoadingPage loadingPage;
    private ControlSubThread subThread;
    private boolean gameEnded = false;

    public ShooterGame() {
        glu = new GLU();
        canvas = new GLCanvas();
        frame = new Frame("ThePirateShip");
        animator = new Animator(canvas);
        gameLevels = new GameLevels();
    }

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();  // Reset The View
        //calculate fps
        fps.updateTime(System.currentTimeMillis() - startTime);
        System.out.println(FPS.fps);
        startTime = System.currentTimeMillis();
        if (needHelp) {
            animator.pause();
            help.showHelp();
        } else if(starting) {
            startingMenu.draw();
        }else if (!character.getAlive() || gameEnded) {
            //animator.stop();
            if (gameEnded)
                gameOver.draw("Finished The Game");
            else
                gameOver.draw("You're Dead!!");
            if (nextLevel) {
                nextLevel = false;
                loadingPage =  new LoadingPage(canvas.getGraphics(), frame.getHeight(),
                        frame.getWidth(), "resources/pirate_ship.jpg");
                this.subThread= new ControlSubThread(canvas, loadingPage);
                this.subThread.start();
                loadLevel(gl);
            }
        } else if (startAnimation.toStop()) {
            if (nextLevel) {
                nextLevel = false;
//                level = new Level(this.factory, this, loader, gl);
//                level.BuildLevel(gameLevels.getLevelsList().get(levelNum));
                loadingPage =  new LoadingPage(canvas.getGraphics(), frame.getHeight(),
                        frame.getWidth(), "resources/pirate_ship.jpg");
                this.subThread= new ControlSubThread(canvas, loadingPage);
                this.subThread.start();
                loadLevel(gl);
            }
            character.step(movement);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_LINEAR);
            gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_LINEAR);
            Vector origin = cooSystem.getOrigin();
            Vector lookat = origin.minus(cooSystem.getZ());
            lookat.normal();
            Vector y = cooSystem.getY();
            glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2),
                    y.getVec()[0], y.getVec()[1], y.getVec()[2]);



            level.updatePos(origin);
            level.updateRooms();
            level.drawRooms();


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
        loadingPage =  new LoadingPage(canvas.getGraphics(), frame.getHeight(),
                frame.getWidth(), "resources/pirate_ship.jpg");
        this.subThread= new ControlSubThread(canvas, loadingPage);
        subThread.start();
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.3f, 1f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL2.GL_DEPTH_TEST);              // Enables Depth Testing
        gl.glDepthFunc(GL2.GL_LEQUAL);               // The Type Of Depth Testing To Do
        // Really Nice Perspective Calculations
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_LIGHTING);

        // Light
        float	ambient[] = {0.8f,0.8f,0.8f,1.0f};
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
        if (firstInit) {
            this.factory = new LoaderFactory(this.loader, gl);
            firstInit = false;
            gameOver = new GameOver(gl);
            help = new Help();
            startAnimation = new StartAnimation(gl, loader);
            startingMenu = new StartingMenu(gl);
        }

        loadLevel(gl);

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
    public void loadLevel(GL2 gl){
        this.cooSystem =  new CoordinateSystem();
        level = new Level(this.factory,this,loader,gl);
        level.BuildLevel(gameLevels.getLevelsList().get(levelNum));
        subThread.stop();
        //Setting the character
        sword = new Sword("objects/RzR/rzr.obj",level,this.factory);
        float[] pos = {0f,5f,-10f};
        sword.setStartPos(pos);
        //level.addModel(sword);
        this.character = new Character(sword,this.cooSystem,gl);
        character.setCurrentLevel(level);
        level.setCharacter(character);
        startTime = System.currentTimeMillis();
    }

    public void currentLevelEnded() {
        if (levelNum != gameLevels.numOfLevels() - 1) {
            levelNum++;
            this.nextLevel = true;
        } else {
            gameEnded = true;
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
        glu.gluPerspective(80.0f, h, 1.0, 160.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void keyPressed(KeyEvent e) {
        float step = 20f;
        double angle = Math.PI/36;

        int keyPressed = e.getKeyCode();
        switch (keyPressed) {
            case KeyEvent.VK_W:
                movement[2] = -step;
                break;
            case KeyEvent.VK_A:
                movement[0] = -step;
                break;
            case KeyEvent.VK_D:
                movement[0] = step;
                break;
            case KeyEvent.VK_S:
                movement[2] = step;
                break;
            case KeyEvent.VK_RIGHT:
                rotate[1] = angle;
                break;
            case KeyEvent.VK_LEFT:
                rotate[1] = -angle;
                break;
            case KeyEvent.VK_R:
                this.character.reload();
                break;
            case KeyEvent.VK_F2:
                this.level.levelEnded();
                break;
            case KeyEvent.VK_SPACE:
                character.attack();
                break;
            case KeyEvent.VK_Q:
                if (starting) {
                    exit();
                }
                character.changeWeapon();
                break;
            case KeyEvent.VK_2:
                if (!character.getAlive())
                    exit();
                break;
            case KeyEvent.VK_1:
                if (!character.getAlive()) {
                    this.levelNum = 0;
                    this.nextLevel = true;
                }
                break;
            case KeyEvent.VK_F1:
                needHelp = true;
                break;
            case KeyEvent.VK_ESCAPE:
                startTime = System.currentTimeMillis();
                needHelp = false;
                animator.resume();
                break;
            case KeyEvent.VK_ENTER:
                starting = false;
                animator.start();
            default:
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                movement[2] = 0;
                break;
            case KeyEvent.VK_A:
                movement[0] = 0;

                break;
            case KeyEvent.VK_D:
                movement[0] = 0;

                break;
            case KeyEvent.VK_S:
                movement[2] = 0;

                break;
            case KeyEvent.VK_RIGHT:
                rotate[1] = 0;
                break;
            case KeyEvent.VK_LEFT:
                rotate[1] = -0;
                break;
        }
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
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        exit();
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        canvas.requestFocus();
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                loadingPage.setSize(e.getComponent().getSize());
                loadingPage.reSized();
            }
        });
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
        moveCamera(mouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        moveCamera(mouseEvent);
    }

    private void moveCamera(MouseEvent mouseEvent) {
        if (!needHelp) {
            double[] mPos = {mouseEvent.getX(), mouseEvent.getY(), 1};
            if (mPos[0] > frame.getWidth()) {
                mPos[0] = frame.getWidth();
            }
            if (mPos[1] > frame.getHeight()) {
                mPos[1] = frame.getHeight();
            }
            if (mPos[0] < 0) {
                mPos[0] = 0;
            }
            if (mPos[1] < 0) {
                mPos[1] = 0;
            }
            double angle = Math.PI * 4 / (double) frame.getWidth();
            double diff = mPos[0] - mousePos[0];
            character.rotate('y', diff * angle);
            mousePos = mPos;
        }
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