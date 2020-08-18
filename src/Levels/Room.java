/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
package Levels;

import LinearMath.Vector;
import Models.IModel;

import javax.media.opengl.GL2;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private List<IModel> roomObjects;
    private Vector leftFront;
    private int length;
    private int width;
    private int roomNumber;
    private List<Integer> roomsToDisplay = new ArrayList<>();

    public List<Integer> getRoomsToDisplay() {
        return roomsToDisplay;
    }

    public Vector getLeftFront() {
        return leftFront;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public Room(int roomNumber)
    {
        this.roomNumber = roomNumber;
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

    public void setBoundaries(String[] data) {
        double[] leftFrontD = {Double.parseDouble(data[1]),Double.parseDouble(data[2]),Double.parseDouble(data[3]) };
        this.leftFront = new Vector(leftFrontD,3);
        this.length = Integer.parseInt(data[4]);
        this.width = Integer.parseInt(data[5]);
    }
    public void addRoomToDisplay(int roomNum){
        this.roomsToDisplay.add(roomNum);
    }
}
