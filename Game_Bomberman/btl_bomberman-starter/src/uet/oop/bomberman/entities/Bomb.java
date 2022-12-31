package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.explosion.Brick_Explosion;
import uet.oop.bomberman.entities.explosion.Explosion;
import uet.oop.bomberman.entities.explosion.Horiontal;
import uet.oop.bomberman.entities.explosion.Vertical;
import uet.oop.bomberman.graphics.Sprite;


import static uet.oop.bomberman.BombermanGame.*;

public class Bomb extends Entity {
    private int timeloop;
    public static int lever =1;


    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void setLever(int lever) {
        this.lever = lever;
    }

    @Override
    public void update() {
        animation();

    }

    // hoat anh bomb no
    public void animation() {

        timeloop++;
        if(bomb_canmove()) {
            canmove[x/Sprite.SCALED_SIZE][y/Sprite.SCALED_SIZE] = true;
        }
        if (timeloop % 20 == 0) {
            img = Sprite.bomb_1.getFxImage();
        }
        if (timeloop % 20 == 10) {
            img = Sprite.bomb_2.getFxImage();
        }

        if (timeloop == 120) {
           destroy();
            img = Sprite.bomb_exploded.getFxImage();
        }
        if (timeloop == 125) {
            img = Sprite.bomb_exploded1.getFxImage();
        }
        if (timeloop == 130) {
            img = Sprite.bomb_exploded2.getFxImage();

        }

        if (timeloop == 140) {
            BombermanGame.entities.remove(this);
            Bomber.bombList.remove(this);
            canmove[x/Sprite.SCALED_SIZE][y/Sprite.SCALED_SIZE] = false;
            timeloop = 0;
        }
    }

    // no bomb + pha bick
    public void destroy() {
        int bombX = (x) / Sprite.SCALED_SIZE;
        int bombY = (y) / Sprite.SCALED_SIZE;
        check_entiny(bombX,bombY);
        if (!canmove[bombX + 1][bombY]) {
            Explosion explosion = new Horiontal(bombX+1,bombY);
            entities.add(explosion);
            if(lever==2) {
                if (!canmove[bombX + 2][bombY]) {
                    Explosion explosion2 = new Horiontal(bombX + 2, bombY);
                    entities.add(explosion2);
                }
                else {
                    check_brick(bombX+2,bombY);
                }
            }

        } else {
            check_brick(bombX + 1, bombY);

        }

        if (!canmove[bombX - 1][bombY]) {
            Explosion explosion = new Horiontal(x/Sprite.SCALED_SIZE-1,y/Sprite.SCALED_SIZE);
            entities.add(explosion);
            if(lever==2) {
                if (!canmove[bombX - 2][bombY]) {
                    Explosion explosion2 = new Horiontal(bombX - 2, bombY);
                    entities.add(explosion2);
                }
                else {
                    check_brick(bombX - 2, bombY);

                }

            }

        } else {
            check_brick(bombX - 1, bombY);

        }
        if (!canmove[bombX][bombY - 1]) {
            Explosion explosion = new Vertical(x/Sprite.SCALED_SIZE,y/Sprite.SCALED_SIZE-1);
            entities.add(explosion);
            if(lever==2) {
                if (!canmove[bombX ][bombY-2]) {
                    Explosion explosion2 = new Vertical(bombX , bombY-2);
                    entities.add(explosion2);
                }
                else {
                    check_brick(bombX,bombY-2);
                }
            }
        } else {
            check_brick(bombX, bombY - 1);

        }
        if (!canmove[bombX][bombY + 1]) {
            Explosion explosion = new Vertical(x/Sprite.SCALED_SIZE,y/Sprite.SCALED_SIZE+1);
            entities.add(explosion);
            if(lever==2) {
                if (!canmove[bombX ][bombY+2]) {
                    Explosion explosion2 = new Vertical(bombX , bombY+2);
                    entities.add(explosion2);
                }
                else {
                    check_brick(bombX,bombY+2);
                }
            }
        } else {
            check_brick(bombX, bombY + 1);

        }
    }

    // check xem co bick trong pham vi no khong
    public void check_brick(int bombX, int bombY) {
        if (stillObjects.get(25 * (bombY) + bombX) instanceof Brick)  {
            Entity entity = new Brick_Explosion(bombX,bombY,Sprite.brick_exploded.getFxImage());
            entities.add(entity);
                stillObjects.remove(25 * (bombY) + bombX);
                stillObjects.add(25 * (bombY) + bombX, new Grass(bombX, bombY, Sprite.grass.getFxImage()));
                canmove[bombX][bombY] = false;

        }
    }
    // check bomber and enemy
    public void check_entiny(int bombX, int bombY) {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            if(((entities.get(i).x/Sprite.SCALED_SIZE)==bombX&&(entities.get(i).y/Sprite.SCALED_SIZE)==bombY)
                    ||((entities.get(i).x+Sprite.SCALED_SIZE-1)/Sprite.SCALED_SIZE)==bombX
                    &&((entities.get(i).y+Sprite.SCALED_SIZE-1)/Sprite.SCALED_SIZE)==bombY) {
                entities.get(i).setLives(false);
            }
        }
    }
    public boolean bomb_canmove() {
   int bombX = x/48;
   int bombY = y/48;
            int size = entities.size();
            for (int i = 0; i < size; i++) {
                int left_up_X = entities.get(i).getX();
                int left_up_Y = entities.get(i).getY();
                int left_down_Y = left_up_Y + Sprite.SCALED_SIZE;
                int right_up_X = left_up_X+Sprite.SCALED_SIZE;

                if(((left_up_X/Sprite.SCALED_SIZE==bombX|| right_up_X/Sprite.SCALED_SIZE==bombX)
                        &&(left_up_Y/Sprite.SCALED_SIZE==bombY||left_down_Y/Sprite.SCALED_SIZE==bombY))
                        && !(entities.get(i).equals(this))
                ) {
                    return false;
                }

            }
            return true;
        }


}