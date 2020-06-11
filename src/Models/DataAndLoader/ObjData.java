package Models.DataAndLoader;

import com.jogamp.opengl.util.texture.Texture;
import javax.media.opengl.GL2;

public class ObjData {
    private Texture texture;
    private int list;
    private Material material;
    public ObjData() { }

    public void setMaterial(Material material) {
        this.material = material;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setList(int list){
        this.list = list;
    }


    public void draw(GL2 gl){
        if(texture != null)
            texture.bind(gl);
        gl.glCallList(list);
        //TODO add material handler
    }
}

