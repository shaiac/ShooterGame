package Levels;

import CollisionDetection.CollisionDetector;
import CollisionDetection.ICollisionObj;
import Game.Character;
import Game.ShooterGame;
import LinearMath.Vector;
import Models.DataAndLoader.LoaderFactory;
import Models.DataAndLoader.ObjectLoader;
import Models.Enemys.Enemy;
import Models.Enemys.JackSparrow;
import Models.Enemys.OldPirate;
import Models.IModel;
import Models.Objects.*;
import Models.Objects.Map;
import Models.Objects.Wall;
import Models.Weapons.*;
import Models.goods.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.*;

public class Level {
    private List<Wall> levelWalls;
    private List<Room> rooms;
    private int roomNumber;
    private ObjectLoader loader;
    private GL2 gl;
    private ShooterGame levelObserver;
    //private Cannon tmpCannon;
    private List<Enemy> enemies;
    private LoaderFactory factory;
    private HashMap<Integer, List<IModel>> addQueue = new HashMap<>();
    private boolean addValid = true;
    private HashMap<Integer, List<IModel>> removeQueue = new HashMap<>();
    private boolean removeValid = true;
    CollisionDetector detector = new CollisionDetector();
    private Character character;

    public Level(ObjectLoader loader, GL2 gl, ShooterGame shooterGame) {
        rooms = new ArrayList<>();
        levelWalls = new ArrayList<>();
        roomNumber = 0;
        this.loader = loader;
        this.gl = gl;
        this.enemies = new ArrayList<>();
        this.levelObserver = shooterGame;
    }
    public Level(LoaderFactory factory, ShooterGame shooterGame){
        rooms = new ArrayList<>();
        levelWalls = new ArrayList<>();
        roomNumber = 0;
        this.enemies = new ArrayList<>();
        this.levelObserver = shooterGame;
        this.factory = factory;
    }
    public Level(LoaderFactory factory,ShooterGame shooterGame, ObjectLoader loader,GL2 gl){
        rooms = new ArrayList<>();
        levelWalls = new ArrayList<>();
        roomNumber = 0;
        this.loader = loader;
        this.gl = gl;
        this.enemies = new ArrayList<>();
        this.levelObserver = shooterGame;
        this.factory = factory;
    }

    public void setCharacter(Character character) {
        this.character = character;
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
        Enemy enemy = null;
        try {
            String[] splitData;
            String data;
            //while there are still lines in the file.
            while ((data = buffer.readLine()) != null) {
                data = data.trim();
                splitData = data.split(" ");
                if (splitData[0].contains("#"))
                    continue;
                if (data.contains("ROOM")) {
                    rooms.add(roomNumber, new Room());
                    this.addQueue.put(roomNumber,new ArrayList<>());
                    this.removeQueue.put(roomNumber,new ArrayList<>());
                    roomNumber++;
                }
                switch (splitData[0]) {
                    case "wall":
                        Wall wall = createWall(data);
                        levelWalls.add(wall);
                        rooms.get(roomNumber - 1).addModel(wall);
                        break;
                    case "AK_47":
                        String path = splitData[1];
                        Ak47 ak47 = new Ak47(path,this,this.factory);
                        //Ak47 ak47 = new Ak47(splitData[1], this);
                        float[] akPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //ak47.create(loader, gl, akPos);
                        ak47.setStartPos(akPos);
                        rooms.get(roomNumber - 1).addModel(ak47);
                        break;
                    case "Barrel":
                        Barrel barrel = new Barrel("objects/barrel/barrel_obj.obj",factory);
                        //Barrel barrel = new Barrel("objects/barrel/barrel_obj.obj");
                        float[] barrelPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //barrel.create(loader, gl, barrelPos);
                        barrel.setStartPos(barrelPos);
                        rooms.get(roomNumber - 1).addModel(barrel);
                        break;
                    //enemies weapons
                    case "Gun":
                        Gun gun = new Gun(splitData[1],this,this.factory);
                        //Gun gun = new Gun(splitData[1], this);
                        float[] gunPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //gun.create(loader, gl, gunPos);
                        gun.setStartPos(gunPos);
                        gun.rotate(Float.parseFloat(splitData[5]), 'x');
                        if(enemy != null){
                            enemy.addWeapon(gun);
                            enemy = null;
                        } else {
                            rooms.get(roomNumber - 1).addModel(gun);
                        }
                        break;
                    case "Cannon":
                        Cannon cannon = new Cannon(splitData[1],this,this.factory);
                        //Cannon cannon = new Cannon(splitData[1], this);
                        //this.tmpCannon = cannon;
                        float[] cannonPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //cannon.create(loader, gl, cannonPos);
                        cannon.setStartPos(cannonPos);
                        cannon.rotate(Float.parseFloat(splitData[5]), 'x');
                        if(enemy != null){
                            enemy.addWeapon(cannon);
                            enemy = null;
                        } else {
                            rooms.get(roomNumber - 1).addModel(cannon);
                        }
                        break;
                    // enemies
                    case "JackSparrow":
                        JackSparrow jack = new JackSparrow(splitData[1],this.factory, this);
                        //JackSparrow jack = new JackSparrow(splitData[1]);
                        float[] jackPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //jack.create(loader, gl, jackPos);
                        //jack.rotate(Float.parseFloat(splitData[5]), 'y');
                        jack.setStartPos(jackPos);
                        rooms.get(roomNumber - 1).addModel(jack);
                        enemies.add(jack);
                        enemy = jack;

                        break;
                    case "OldPirate":
                        OldPirate pirate = new OldPirate(splitData[1],this,this.factory);
                        //OldPirate pirate = new OldPirate(splitData[1]);
                        float[] piratePos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //pirate.create(loader, gl, piratePos);
                        //pirate.rotate(Float.parseFloat(splitData[5]), 'z');
                        pirate.setStartPos(piratePos);
                        rooms.get(roomNumber - 1).addModel(pirate);
                        enemies.add(pirate);
                        enemy = pirate;
                        break;
                    case "Shotgun":
                        Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", this,this.factory);
                        //Shotgun shotgun = new Shotgun("objects/Shotgun/GunTwo.obj", this);
                        float[] shotgunPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //shotgun.create(loader, gl, shotgunPos);
                        shotgun.setStartPos(shotgunPos);
                        rooms.get(roomNumber - 1).addModel(shotgun);
                        break;
                    case "Sword":
                        Sword sword = new Sword("objects/RzR/rzr.obj",this,this.factory);
                        //Sword sword = new Sword("objects/RzR/rzr.obj");
                        float[] swordPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //sword.create(loader, gl, swordPos);
                        sword.setStartPos(swordPos);
                        rooms.get(roomNumber - 1).addModel(sword);
                        break;
                    case "Treasure":
                        Treasure treasure = new Treasure(splitData[1],this,this.factory);
                        //Treasure treasure = new Treasure(splitData[1]);
                        float[] treasurePos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //treasure.create(loader, gl, treasurePos);
                        treasure.setStartPos(treasurePos);
                        treasure.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).addModel(treasure);
                        break;
                    case "Map":
                        Map map = new Map(splitData[1],this.factory);
                        //Map map = new Map(splitData[1]);
                        float[] mapPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //map.create(loader, gl, mapPos);
                        map.setStartPos(mapPos);
                        map.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).addModel(map);
                        break;
                    case "Skull":
                        Skull skull = new Skull(splitData[1],this.factory);
                        //Skull skull = new Skull(splitData[1]);
                        float[] skullPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //skull.create(loader, gl, skullPos);
                        skull.setStartPos(skullPos);
                        skull.rotate(Float.parseFloat(splitData[5]), 'y');
                        skull.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
                                Float.parseFloat(splitData[6]));
                        rooms.get(roomNumber - 1).addModel(skull);
                    case "Skellington":
                        Skellington skellington = new Skellington(splitData[1],this.factory);
                        //Skellington skellington = new Skellington(splitData[1]);
                        float[] skellingtonPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //skellington.create(loader, gl, skellingtonPos);
                        skellington.setStartPos(skellingtonPos);
                        skellington.rotate(Float.parseFloat(splitData[5]), 'y');
                        skellington.scale(Float.parseFloat(splitData[6]), Float.parseFloat(splitData[6]),
                                Float.parseFloat(splitData[6]));
                        rooms.get(roomNumber - 1).addModel(skellington);
                        break;
                    case "Heart":
                        Heart heart = new Heart(splitData[1],this,this.factory);
                        //Heart heart = new Heart(splitData[1]);
                        float[] heartPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //heart.create(loader, gl, heartPos);
                        heart.setStartPos(heartPos);
                        heart.rotate(Float.parseFloat(splitData[5]), 'y');
                        rooms.get(roomNumber - 1).addModel(heart);
                        break;
                    case "AmmoBox":
                        AmmoBox ammoBox = new AmmoBox(splitData[1],this,this.factory);
                        //AmmoBox ammoBox = new AmmoBox(splitData[1]);
                        float[] ammoPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //ammoBox.create(loader, gl, ammoPos);
                        ammoBox.setStartPos(ammoPos);
                        rooms.get(roomNumber - 1).addModel(ammoBox);
                        break;
                    case "SkullSymbol":
                        SkullSymbol skullSymbol = new SkullSymbol(splitData[1],this.factory);
                        //SkullSymbol skullSymbol = new SkullSymbol(splitData[1]);
                        float[] symbolPos = {Float.parseFloat(splitData[2]), Float.parseFloat(splitData[3]),
                                Float.parseFloat(splitData[4])};
                        //skullSymbol.create(loader, gl, symbolPos);
                        skullSymbol.setStartPos(symbolPos);
                        skullSymbol.rotate(Float.parseFloat(splitData[5]), 'x');
                        rooms.get(roomNumber - 1).addModel(skullSymbol);
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
        if(splitData.length == 9){
            wall.getCollisionData().checkCollision = false;
        }
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
        //rooms.get(0).addModel(model);
        addValid = false;
        addQueue.get(0).add(model);
    }
    public void removeModel(IModel model){
        removeValid = false;
        removeQueue.get(0).add(model);
    }
    public void updateRooms(){
        if(!addValid) {
            for (Integer roomNum : addQueue.keySet()) {
                for (IModel model : addQueue.get(roomNum)) {
                    rooms.get(roomNum).addModel(model);
                }
                addQueue.get(roomNum).clear();
            }
            addValid = true;
        }
        if(!removeValid){
            for (Integer roomNum : removeQueue.keySet()) {
                for (IModel model : removeQueue.get(roomNum)) {
                    for (Room room : rooms) {
                        room.removeModel(model);
                    }
                }
                removeQueue.get(roomNum).clear();
            }
            removeValid = true;
        }
    }
    public void drawRooms() {
        for (Room room : this.rooms) {
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
    public List<Room> getRooms(){
        return rooms;
    }

    public void levelEnded() {
        this.levelObserver.currentLevelEnded(gl);
    }

    public void checkCollision(ICollisionObj collisionObj) {
        if (checkWithModel(collisionObj, character)) {
            return;
        }
        for (Room room : rooms) {
            for (IModel model : room.getRoomObjects()) {
                if (model instanceof ICollisionObj) {
//                    if (detector.CheckCollision(collisionObj.getCollisionData(),
//                            ((ICollisionObj)model).getCollisionData())) {
//                        ((ICollisionObj) model).collide(collisionObj);
//                        collisionObj.collide((ICollisionObj) model);
//                    }
                    if (checkWithModel(collisionObj, (ICollisionObj)model)) {
                        return;
                    }
                }
            }
        }
    }
    public boolean checkWithModel(ICollisionObj collisionObj1, ICollisionObj collisionObj2) {
        if (collisionObj1 == collisionObj2) {
        return false;
        }
        if (detector.CheckCollision(collisionObj1.getCollisionData(),
                (collisionObj2.getCollisionData()))) {
            collisionObj2.collide(collisionObj1);
            collisionObj1.collide(collisionObj2);
            return true;
        }
        return false;
    }
}
