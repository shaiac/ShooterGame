package Game;

import LinearMath.Vector;
import Models.DataAndLoader.ObjectLoader;
import Models.PirateShip;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class StartAnimation {
    private boolean stopAnimation = true;
    private GL2 gl;
    private PirateShip pirateShip;
    private CoordinateSystem pirateShipCoor;
    private ObjectLoader loader;

    public StartAnimation(GL2 gl, ObjectLoader loader) {
        this.gl = gl;
        this.pirateShipCoor = new CoordinateSystem();
        this.loader = loader;
        createShip();
    }


    private void createShip() {
        pirateShip = new PirateShip("objects/PirateShip/boat.obj");
        float[] shipPos = {0,-20f,-100f};
        pirateShip.create(loader,gl,shipPos);
        pirateShip.scale(200,200,200);
    }

    public boolean toStop() {
        return stopAnimation;
    }

    public void pirateShipAnimation(GLU glu) {
        Vector origin = pirateShipCoor.getOrigin();
        Vector lookat = origin.minus(pirateShipCoor.getZ());
        Vector y = pirateShipCoor.getY();
        glu.gluLookAt(origin.get(0), origin.get(1), origin.get(2), lookat.get(0), lookat.get(1), lookat.get(2),
                y.getVec()[0], y.getVec()[1], y.getVec()[2]);
        pirateShipCoor.moveStep('z', -0.04);
        pirateShipCoor.moveStep('y', -0.01);
        pirateShip.draw(gl);
        if (origin.get(2) < -90) {
            stopAnimation = true;
        }
    }

}
