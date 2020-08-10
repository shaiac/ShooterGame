package Levels;

import Models.IModel;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<IModel> roomObjects;

    public Room() {
        roomObjects = new ArrayList<>();
    }

    public void addModel(IModel model) {
        roomObjects.add(model);
    }
    public void removeModel(IModel model){
        roomObjects.remove(model);
    }

    public void drawAll(GL2 gl) {
        for (IModel IModel : roomObjects ) {
            IModel.draw(gl);
        }
    }
}
