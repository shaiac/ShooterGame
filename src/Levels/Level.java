package Levels;

import Models.DataAndLoader.ObjectLoader;
import Models.IModel;
import Models.Wall;
import Models.Weapons.Ak47;
import Models.Weapons.Shotgun;
import Models.Weapons.Sword;
import Models.goods.Barrel;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Room> rooms;
    private int roomNumber;
    private ObjectLoader loader;
    private GL2 gl;
    public Level(ObjectLoader loader, GL2 gl) {
        rooms = new ArrayList<>();
        roomNumber = 0;
        this.loader = loader;
        this.gl = gl;
    }
    //read and build the level
    public void BuildLevel(String levelDefinition) {
        Reader reader = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelDefinition);
            reader = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(reader);
            this.readFile(buffer);
        } finally {
            try { // try to close the reader
                reader.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
    }

    private void readFile(BufferedReader buffer) {
        try {
            String[] splitData;
            String data;
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                if (data.contains("ROOM")) {
                    rooms.add(roomNumber, new Room());
                    roomNumber++;
                } if (data.contains("wall")) {
                    rooms.get(roomNumber - 1).AddModel(createWall(data));
                } if (data.contains("OldPirate")) {
                    //rooms.get(roomNumber - 1).AddModel(createOldPirate(data));
                } if (data.contains("AK_47")) {
                    splitData = data.split(" ");
                    Ak47 ak47 = new Ak47(splitData[1]);
                    float[] akPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
                            Float.parseFloat(splitData[4])};
                    ak47.create(loader, gl, akPos);
                    rooms.get(roomNumber - 1).AddModel(ak47);
                } if (data.contains("Barrel")) {
                    splitData = data.split(" ");
                    Barrel barrel = new Barrel("objects/barrel/barrel_obj.obj");
                    float[] barrelPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
                            Float.parseFloat(splitData[4])};
                    barrel.create(loader, gl, barrelPos);
                    rooms.get(roomNumber - 1).AddModel(barrel);
                } if (data.contains("Cannon")) {
                    //rooms.get(roomNumber - 1).AddModel(new Cannon(data));
                } if (data.contains("JackSparrow")) {
                    //rooms.get(roomNumber - 1).AddModel(new JackSparrow(data));
                } if (data.contains("Shotgun")) {
                    splitData = data.split(" ");
                    Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj");
                    float[] shotgunPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
                            Float.parseFloat(splitData[4])};
                    shotgun.create(loader, gl, shotgunPos);
                    rooms.get(roomNumber - 1).AddModel(shotgun);
                } if (data.contains("Sword")) {
                    splitData = data.split(" ");
                    Sword sword = new Sword("objects/RzR/rzr.obj");
                    float[] swordPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
                            Float.parseFloat(splitData[4])};
                    sword.create(loader,gl,swordPos);
                    rooms.get(roomNumber - 1).AddModel(sword);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { // try to close the buffer
                buffer.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
    }

    private Wall createWall(String wallDefinitions) {
        String[] splitData = wallDefinitions.split(" ");
        Wall wall = new Wall(Float.parseFloat(splitData[1]),Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3])
        , splitData[4].charAt(0), Float.parseFloat(splitData[5]),Float.parseFloat(splitData[6]));
        wall.setTex(getTextureFromPath(splitData[7]));
        float[] wallPos = {0,0,0};
        wall.create(loader, gl, wallPos);
        return wall;
    }

    private Texture getTextureFromPath(String texturePath) {
        try {
            //String data[] = texturePath.split("\\.");
            Texture texture = TextureIO.newTexture(new File( texturePath ),true);
            return texture;
        } catch (IOException e) {
            return null;
        }
    }

    public void drawRooms() {
        for (Room room : rooms) {
            room.drawAll(gl);
        }
    }
}
