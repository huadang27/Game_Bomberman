package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.*;

public class Enemy extends Entity {
    private int direction;
    private int timeloop;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    private void randomd() {
        Random random = new Random();
        direction = random.nextInt(4) + 1;
    }

    @Override
    public void update() {
        setSpeed(1);
        timeloop++;
        while (true) {
            if (direction == 1 && canMove_enemy(x + 1, y)) break;
            if (direction == 2 && canMove_enemy(x - 1, y)) break;
            if (direction == 3 && canMove_enemy(x, y + 1)) break;
            if (direction == 4 && canMove_enemy(x, y - 1)) break;
            randomd();
        }
        animation_enemy();
        if (timeloop == 40) {
            randomd();
        }
        ;
        if (timeloop == 40)
            timeloop = 0;
        moving_enemy();

    }

    public void moving_enemy() {

        if (direction == 1) {
            x++;
        }
        if (direction == 2) {
            x--;
        }
        if (direction == 3) {
            y++;
        }
        if (direction == 4) {
            y--;
        }
    }

    public void animation_enemy() {
        if (lives) {
            if (direction == 1 || direction == 3) {
                switch (timeloop % 31) {
                    case 10:
                        img = Sprite.balloom_right1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.balloom_right2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.balloom_right3.getFxImage();
                }
            }
            if (direction == 2 || direction == 4) {
                switch (timeloop % 31) {
                    case 10:
                        img = Sprite.balloom_left1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.balloom_left2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.balloom_left3.getFxImage();
                }
            }
        } else {

            timeDie++;
            direction = 5;
            img = Sprite.balloom_dead.getFxImage();
        }
        if (timeDie == 30) {
point+=100;
            timeDie = 0;
            BombermanGame.entities.remove(this);

        }
    }
}


