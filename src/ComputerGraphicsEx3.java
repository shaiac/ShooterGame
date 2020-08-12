import CollisionDetection.*;
import Game.ShooterGame;
import LinearMath.Point;
import LinearMath.Vector;
import Tests.CollisionTests;

import java.util.ArrayList;
import java.util.List;

/*
submit:
Ziv Zaarur 206099913
Shai Acoca 315314278
 */
public class ComputerGraphicsEx3 {
    public static void main(String[] args) {
        do {
            ShooterGame shooterGame = new ShooterGame();
            shooterGame.startGame();
            while (shooterGame.playerDecision != 1);
        } while (true);
        //CollisionTests collisionTests = new CollisionTests();
        //collisionTests.test();
    }
}
