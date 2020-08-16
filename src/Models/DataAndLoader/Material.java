package Models.DataAndLoader;

import javax.media.opengl.GL2;

public class Material {
    private float ns;
    private float[] ka = null;
    private float[] kd = null;
    private float[] ks = null;
    private float ni;
    private float d;
    private float illum;

    public void setNs(float ns) {
        this.ns = ns;
    }

    public void setKa(float[] ka) {
        float[] color = {ka[0],ka[1],ka[2],1};
        this.ka = color;
    }

    public void setKd(float[] kd) {
        float[] color = {kd[0],kd[1],kd[2],1};
        this.kd = color;
    }

    public void setKs(float[] ks) {
        float[] color = {ks[0],ks[1],ks[2],1};
        this.ks = color;
    }

    public void setNi(float ni) {
        this.ni = ni;
    }

    public void setD(float d) {
        this.d = d;
    }

    public void setIllum(float illum) {
        this.illum = illum;
    }

    public float getNs() {
        return ns;
    }

    public float[] getKa() {
        return ka;
    }

    public float[] getKd() {
        return kd;
    }

    public float[] getKs() {
        return ks;
    }

    public float getNi() {
        return ni;
    }

    public float getD() {
        return d;
    }

    public float getIllum() {
        return illum;
    }

    public Material() {
    }
    public void draw(GL2 gl){
        if(ka!= null){
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_AMBIENT,ka,0);
        }
        if(kd!= null){
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_DIFFUSE,kd,0);
        }
        if(ks!= null){
            gl.glMaterialfv(GL2.GL_FRONT_AND_BACK,GL2.GL_SPECULAR,ks,0);
        }
    }
}
