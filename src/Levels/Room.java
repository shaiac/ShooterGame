package Levels;

import Models.Model;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<Model> roomObjects;

    public Room() {
        roomObjects = new ArrayList<>();
    }

    public void AddModel(Model model) {
        roomObjects.add(model);
    }

    public void drawAll(GL2 gl) {
        for (Model model: roomObjects ) {
            model.draw(gl);
        }
    }
}
