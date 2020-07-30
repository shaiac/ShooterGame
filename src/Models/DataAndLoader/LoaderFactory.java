package Models.DataAndLoader;

import CollisionDetection.CollisionData;
import CollisionDetection.CollisionType;
import javafx.util.Pair;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoaderFactory {
    private Map<String,List<ObjData>> pathToObj = new HashMap<>();
    private Map<String, CollisionData> pathToCollision = new HashMap<>();
    private ObjectLoader loader;
    private GL2 gl;
    public LoaderFactory(ObjectLoader loader, GL2 gl) {
        this.loader = loader;
        this.gl = gl;
    }
    public Pair<List<ObjData>,CollisionData> create(String path, CollisionType type){
        if(!pathToObj.containsKey(path)){
            this.loadObject(path,type);
        }
        List<ObjData> object = copyObject(this.pathToObj.get(path));
        CollisionData collision = this.pathToCollision.get(path);
        CollisionData collisionData = collision.clone();
        return new Pair<>(object,collisionData);
    }
    public List<ObjData> create (String path){
        if(!pathToObj.containsKey(path)){
            this.loadObject(path);
        }
        return copyObject(this.pathToObj.get(path));
    }

    private void loadObject(String path,CollisionType type){
        List<ObjData> object = this.loader.LoadModelToGL(path,gl,type);
        CollisionData collision = this.loader.getCollisionData();
        this.pathToObj.put(path,object);
        this.pathToCollision.put(path,collision);
    }
    private void loadObject(String path){
        List<ObjData> object = this.loader.LoadModelToGL(path,gl);
        this.pathToObj.put(path,object);
    }
    private List<ObjData> copyObject(List<ObjData> data){
        List<ObjData> newData = new ArrayList<>();
        for (ObjData obj:data) {
            newData.add(new ObjData(obj));
        }
        return newData;
    }
}
