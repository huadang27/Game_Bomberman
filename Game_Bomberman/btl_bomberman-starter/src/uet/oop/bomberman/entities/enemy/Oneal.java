package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import static uet.oop.bomberman.BombermanGame.*;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Entity {
    private int direction;
    private int timeloop;




    public Oneal(int xUnit, int yUnit, Image img) {
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
                        img = Sprite.oneal_right1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.oneal_right2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.oneal_right3.getFxImage();
                }
            }
            if (direction == 2 || direction == 4) {
                switch (timeloop % 31) {
                    case 10:
                        img = Sprite.oneal_left1.getFxImage();
                        break;
                    case 20:
                        img = Sprite.oneal_left2.getFxImage();
                        break;
                    case 30:
                        img = Sprite.oneal_left3.getFxImage();
                }
            }
        } else {
            if(timeDie ==0) {
                img = Sprite.oneal_dead.getFxImage();
                direction = 5;
            }
            timeDie++;


        }
        if (timeDie == 40) {
point+=200;

            int xunit = (x+24)/Sprite.SCALED_SIZE;
            int yunit = (y+24)/Sprite.SCALED_SIZE;
            Entity enemy1 = new Doll(xunit,yunit,Sprite.balloom_left1.getFxImage());
            Entity enemy2 = new Doll(xunit,yunit,Sprite.balloom_left1.getFxImage());
            BombermanGame.entities.add(enemy1);
            BombermanGame.entities.add(enemy2);
           BombermanGame.entities.remove(this);
            timeDie = 0;

        }
    }

}
