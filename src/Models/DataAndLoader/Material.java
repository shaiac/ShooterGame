package Models.DataAndLoader;

public class Material {
    private float ns;
    private float[] ka;
    private float[] kd;
    private float[] ks;
    private float ni;
    private float d;
    private float illum;

    public void setNs(float ns) {
        this.ns = ns;
    }

    public void setKa(float[] ka) {
        this.ka = ka;
    }

    public void setKd(float[] kd) {
        this.kd = kd;
    }

    public void setKs(float[] ks) {
        this.ks = ks;
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
}
