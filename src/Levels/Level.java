package Levels;

import Models.Wall;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Room> rooms;
    private int numberOfRooms;
    public Level() {
        rooms = new ArrayList<>();
        numberOfRooms = 0;
    }
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
            String data;
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                if (data.contains("ROOM")) {
                    rooms.add(numberOfRooms, new Room());
                    numberOfRooms++;
                } if (data.contains("wall")) {
                    Wall wall = createWall(data);
                    rooms.get(numberOfRooms - 1).AddModel(createWall(data));
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
        //wall.setTex(splitData[7]);
        return wall;
    }

//    private Texture getTextureFromPath(String texturePath) {
//        try {
//            InputStream myis = ClassLoader.getSystemClassLoader().getResourceAsStream(texturePath);
//            String data[] = texturePath.split("\\.");
//            Texture tex =  TextureIO.newTexture(myis, true, data[1]);
//            return tex;
//        } catch(Exception e){
//            System.out.println("ffff");
//        }
//        return null;
//    }
}
