package Levels;

import Game.ShooterGame;
import LinearMath.Vector;
import Models.DataAndLoader.ObjectLoader;
import Models.Enemys.Enemy;
import Models.Enemys.JackSparrow;
import Models.Enemys.OldPirate;
import Models.IModel;
import Models.Wall;
import Models.Weapons.*;
import Models.goods.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Wall> levelWalls;
    private List<Room> rooms;
    private int roomNumber;
    private ObjectLoader loader;
    private GL2 gl;
    //private Cannon tmpCannon;
    private List<Enemy> enemies;
    public Level(ObjectLoader loader, GL2 gl, ShooterGame shooterGame) {
        rooms = new ArrayList<>();
        levelWalls = new ArrayList<>();
        roomNumber = 0;
        this.loader = loader;
        this.gl = gl;
        this.enemies = new ArrayList<>();
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

//    private void readFile(BufferedReader buffer) {
//        try {
//            String[] splitData;
//            String data;
//            //while there are still lines in the file.
//            while ((data = buffer.readLine()) != null) {
//                data = data.trim();
//                if (data.contains("ROOM")) {
//                    rooms.add(roomNumber, new Room());
//                    roomNumber++;
//                } else if (data.contains("wall")) {
//                    Wall wall = createWall(data);
//                    levelWalls.add(wall);
//                    rooms.get(roomNumber - 1).AddModel(wall);
//                } else if (data.contains("OldPirate")) {
//                    //rooms.get(roomNumber - 1).AddModel(createOldPirate(data));
//                } else if (data.contains("AK_47")) {
//                    splitData = data.split(" ");
//                    Ak47 ak47 = new Ak47(splitData[1], this);
//                    float[] akPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    ak47.create(loader, gl, akPos);
//                    rooms.get(roomNumber - 1).AddModel(ak47);
//                } else if (data.contains("Barrel")) {
//                    splitData = data.split(" ");
//                    Barrel barrel = new Barrel("objects/barrel/barrel_obj.obj");
//                    float[] barrelPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    barrel.create(loader, gl, barrelPos);
//                    rooms.get(roomNumber - 1).AddModel(barrel);
//                } else if (data.contains("Cannon")) {
//                    splitData = data.split(" ");
//                    Cannon cannon = new Cannon(splitData[1], this);
//                    this.tmpCannon = cannon;
//                    float[] cannonPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    cannon.create(loader,gl,cannonPos);
//                    cannon.rotate(Float.parseFloat(splitData[5]), 'x');
//                    rooms.get(roomNumber - 1).AddModel(cannon);
//                } else if (data.contains("JackSparrow")) {
//                    //rooms.get(roomNumber - 1).AddModel(new JackSparrow(data));
//                } else if (data.contains("Shotgun")) {
//                    splitData = data.split(" ");
//                    Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", this);
//                    float[] shotgunPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    shotgun.create(loader, gl, shotgunPos);
//                    rooms.get(roomNumber - 1).AddModel(shotgun);
//                } else if (data.contains("Sword")) {
//                    splitData = data.split(" ");
//                    Sword sword = new Sword("objects/RzR/rzr.obj");
//                    float[] swordPos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    sword.create(loader,gl,swordPos);
//                    rooms.get(roomNumber - 1).AddModel(sword);
//                } else if (data.contains("Treasure")) {
//                    splitData = data.split(" ");
//                    Treasure treasure = new Treasure(splitData[1]);
//                    float[] treasurePos = {Float.parseFloat(splitData[2]),Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    treasure.create(loader,gl,treasurePos);
//                    treasure.rotate(Float.parseFloat(splitData[5]), 'y');
//                    rooms.get(roomNumber - 1).AddModel(treasure);
//                } else if (data.contains("Map")) {
//                    splitData = data.split(" ");
//                    Map map = new Map(splitData[1]);
//                    float[] mapPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    map.create(loader, gl, mapPos);
//                    map.rotate(Float.parseFloat(splitData[5]), 'y');
//                    rooms.get(roomNumber - 1).AddModel(map);
//                } else if (data.contains("Skull")) {
//                    splitData = data.split(" ");
//                    Skull skull = new Skull(splitData[1]);
//                    float[] skullPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    skull.create(loader, gl, skullPos);
//                    skull.rotate(Float.parseFloat(splitData[5]), 'y');
//                    skull.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
//                            Float.parseFloat(splitData[6]));
//                    rooms.get(roomNumber - 1).AddModel(skull);
//                } else if (data.contains("Skellington")) {
//                    splitData = data.split(" ");
//                    Skellington skellington = new Skellington(splitData[1]);
//                    float[] skellingtonPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    skellington.create(loader, gl, skellingtonPos);
//                    skellington.rotate(Float.parseFloat(splitData[5]), 'y');
//                    skellington.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
//                            Float.parseFloat(splitData[6]));
//                    rooms.get(roomNumber - 1).AddModel(skellington);
//                } else if (data.contains("Heart")) {
//                    splitData = data.split(" ");
//                    Heart heart = new Heart(splitData[1]);
//                    float[] heartPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    heart.create(loader, gl, heartPos);
//                    heart.rotate(Float.parseFloat(splitData[5]), 'y');
//                    rooms.get(roomNumber - 1).AddModel(heart);
//                } else if (data.contains("AmmoBox")) {
//                    splitData = data.split(" ");
//                    AmmoBox ammoBox = new AmmoBox(splitData[1]);
//                    float[] ammoPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    ammoBox.create(loader, gl, ammoPos);
//                    //ammoBox.rotate(Float.parseFloat(splitData[5]), 'y');
//                    rooms.get(roomNumber - 1).AddModel(ammoBox);
//                } else if (data.contains("SkullSymbol")) {
//                    splitData = data.split(" ");
//                    SkullSymbol skullSymbol = new SkullSymbol(splitData[1]);
//                    float[] symbolPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
//                            Float.parseFloat(splitData[4])};
//                    skullSymbol.create(loader, gl, symbolPos);
//                    skullSymbol.rotate(Float.parseFloat(splitData[5]), 'x');
//                    rooms.get(roomNumber - 1).AddModel(skullSymbol);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try { // try to close the buffer
//                buffer.close();
//            } catch (IOException e) {
//                System.err.println("Couldn't close reader");
//                e.printStackTrace();
//            }
//        }
//    }

    private void readFile(BufferedReader buffer) {
        Enemy enemy = null;
        try {
            String[] splitData;
            String data;
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                splitData = data.split(" ");
                if (data.contains("ROOM")) {
                    rooms.add(roomNumber, new Room());
                    roomNumber++;
                }
                switch (splitData[0]) {
                    case "wall":
                        Wall wall = createWall(data);
                        levelWalls.add(wall);
                        rooms.get(roomNumber - 1).AddModel(wall);
                        break;
                    case "AK_47":
                        Ak47 ak47 = new Ak47(splitData[1], this);
                        float[] akPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        ak47.create(loader, gl, akPos);
                        rooms.get(roomNumber - 1).AddModel(ak47);
                        break;
                    case "Barrel":
                        Barrel barrel = new Barrel("objects/barrel/barrel_obj.obj");
                        float[] barrelPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        barrel.create(loader, gl, barrelPos);
                        rooms.get(roomNumber - 1).AddModel(barrel);
                        break;
                    //enemies weapons
                    case "Gun":
                        Gun gun = new Gun(splitData[1], this);
                        float[] gunPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        gun.create(loader, gl, gunPos);
                        gun.rotate(Float.parseFloat(splitData[5]), 'x');
                        if(enemy != null){
                            enemy.addWeapon(gun);
                            enemy = null;
                        } else {
                            rooms.get(roomNumber - 1).AddModel(gun);
                        }
                        break;
                    case "Cannon":
                        Cannon cannon = new Cannon(splitData[1], this);
                        //this.tmpCannon = cannon;
                        float[] cannonPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        cannon.create(loader, gl, cannonPos);
                        cannon.rotate(Float.parseFloat(splitData[5]), 'x');
                        if(enemy != null){
                            enemy.addWeapon(cannon);
                            enemy = null;
                        } else {
                            rooms.get(roomNumber - 1).AddModel(cannon);
                        }
                        break;
                    // enemies
                    case "JackSparrow":
                        JackSparrow jack = new JackSparrow(splitData[1]);
                        float[] jackPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        jack.create(loader, gl, jackPos);
                        //jack.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).AddModel(jack);
                        enemies.add(jack);
                        enemy = jack;

                        break;
                    case "OldPirate":
                        OldPirate pirate = new OldPirate(splitData[1]);
                        float[] piratePos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        pirate.create(loader, gl, piratePos);
                        //pirate.rotate(Float.parseFloat(splitData[5]), 'z');
                        rooms.get(roomNumber - 1).AddModel(pirate);
                        enemies.add(pirate);
                        enemy = pirate;
                        break;
                    case "Shotgun":
                        Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", this);
                        float[] shotgunPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        shotgun.create(loader, gl, shotgunPos);
                        rooms.get(roomNumber - 1).AddModel(shotgun);
                        break;
                    case "Sword":
                        Sword sword = new Sword("objects/RzR/rzr.obj");
                        float[] swordPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        sword.create(loader, gl, swordPos);
                        rooms.get(roomNumber - 1).AddModel(sword);
                        break;
                    case "Treasure":
                        Treasure treasure = new Treasure(splitData[1]);
                        float[] treasurePos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        treasure.create(loader, gl, treasurePos);
                        treasure.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).AddModel(treasure);
                        break;
                    case "Map":
                        Map map = new Map(splitData[1]);
                        float[] mapPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        map.create(loader, gl, mapPos);
                        map.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).AddModel(map);
                        break;
                    case "Skull":
                        Skull skull = new Skull(splitData[1]);
                        float[] skullPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        skull.create(loader, gl, skullPos);
                        skull.rotate(Float.parseFloat(splitData[5]), 'y');
                        skull.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
                                Float.parseFloat(splitData[6]));
                        rooms.get(roomNumber - 1).AddModel(skull);
                    case "Skellington":
                        Skellington skellington = new Skellington(splitData[1]);
                        float[] skellingtonPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        skellington.create(loader, gl, skellingtonPos);
                        skellington.rotate(Float.parseFloat(splitData[5]), 'y');
                        skellington.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
                                Float.parseFloat(splitData[6]));
                        rooms.get(roomNumber - 1).AddModel(skellington);
                        break;
                    case "Heart":
                        Heart heart = new Heart(splitData[1]);
                        float[] heartPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        heart.create(loader, gl, heartPos);
                        heart.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).AddModel(heart);
                        break;
                    case "AmmoBox":
                        AmmoBox ammoBox = new AmmoBox(splitData[1]);
                        float[] ammoPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        ammoBox.create(loader, gl, ammoPos);
                        //ammoBox.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).AddModel(ammoBox);
                        break;
                    case "SkullSymbol":
                        SkullSymbol skullSymbol = new SkullSymbol(splitData[1]);
                        float[] symbolPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        skullSymbol.create(loader, gl, symbolPos);
                        skullSymbol.rotate(Float.parseFloat(splitData[5]), 'x');
                        rooms.get(roomNumber - 1).AddModel(skullSymbol);
                        break;



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

    /*public Cannon getTmpCannon () {
        return this.tmpCannon;
    }*/

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
            InputStream myis = ClassLoader.getSystemClassLoader().getResourceAsStream(texturePath);
            String data[] = texturePath.split("\\.");
            return TextureIO.newTexture(myis, true, data[1]);
            //Texture texture = TextureIO.newTexture(new File( texturePath ),true);
            //return texture;
        } catch (IOException e) {
            return null;
        }
    }

    public void addModel(IModel model) {
        rooms.get(0).AddModel(model);
    }

    public void drawRooms() {
        for (Room room : rooms) {
            room.drawAll(gl);
        }
    }
    public void updatePos(Vector origin){
        for (Enemy enemy:enemies) {
            enemy.updateOrigin(origin);
        }
    }
    public void drawEnemies(){
        for (Enemy enemy:enemies) {
            enemy.draw(gl);
        }
    }
    public List<Wall> getLevelWalls(){
        return levelWalls;
    }
}
