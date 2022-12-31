package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import static uet.oop.bomberman.BombermanGame.*;

import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Entity {
    private int direction ;
    private int timeloop;
    private int speed =2;



    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    private void randomd() {
        Random random = new Random();
        direction = random.nextInt(4) + 1;
    }

    @Override
    public void update() {
        timeloop++;


        if (x % Sprite.SCALED_SIZE == 0 && y % Sprite.SCALED_SIZE == 0) {
            randomd();
        }



        while (true) {
            //  if(AI()) break;
            if (direction == 1 && canMove_enemy(x + speed, y)) break;
            if (direction == 2 && canMove_enemy(x - speed, y)) break;
            if (direction == 3 && canMove_enemy(x, y + speed)) break;
            if (direction == 4 && canMove_enemy(x, y - speed)) break;
            else
                randomd();
        }
        moving_enemy();
        animation_enemy();
        if (timeloop == 40)
            timeloop = 0;

    }

    public void moving_enemy() {

        if (direction == 1) {
            x += speed;
        }
        if (direction == 2) {
            x -= speed;
        }
        if (direction == 3) {
            y += speed;
        }
        if (direction == 4) {
            y -= speed;
        }
    }

    public void animation_enemy() {
        if (lives) {
            if (direction == 1 || direction == 3) {
                switch (timeloop % 31) {
                    case 10:
                        img = Sprite.doll_right1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.doll_right2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.doll_right3.getFxImage();
                }
            }
            if (direction == 2 || direction == 4) {
                switch (timeloop % 31) {
                    case 10:
                        img = Sprite.doll_left1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.doll_left2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.doll_left3.getFxImage();
                }
            }
        } else {
            if(timeDie ==0) {
                img = Sprite.doll_dead.getFxImage();
                direction = 5;
            }
            timeDie++;


        }
        if (timeDie == 40) {
            point+=300;
            BombermanGame.entities.remove(this);
            timeDie = 0;

        }
    }

}
