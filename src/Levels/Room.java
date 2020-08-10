package Levels;

import Models.IModel;

import javax.media.opengl.GL2;
import java.awt.*;
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
    public List<IModel> getRoomObjects() {return roomObjects; }
    public void drawAll(GL2 gl) {
        for (IModel object : roomObjects ) {
            object.draw(gl);
        }
    }
}
