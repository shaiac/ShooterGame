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

    public void AddModel(IModel IModel) {
        roomObjects.add(IModel);
    }

    public void drawAll(GL2 gl) {
        for (IModel IModel : roomObjects ) {
            IModel.draw(gl);
        }
    }
}
