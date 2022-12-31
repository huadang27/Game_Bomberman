package uet.oop.bomberman.entities.explosion;

import javafx.scene.image.Image;

import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public abstract class Explosion extends Entity {
    public Explosion(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void update() {

    }

    public void check_entiny(int bombX, int bombY) {
        int size = entities.size();
        for (int i = 0; i < size; i++) {
            int left_up_X = entities.get(i).getX();
            int left_up_Y = entities.get(i).getY();
            int left_down_Y = left_up_Y + Sprite.SCALED_SIZE;
            int right_up_X = left_up_X+Sprite.SCALED_SIZE;

            if((left_up_X/Sprite.SCALED_SIZE==bombX|| right_up_X/Sprite.SCALED_SIZE==bombX)
                    &&(left_up_Y/Sprite.SCALED_SIZE==bombY||left_down_Y/Sprite.SCALED_SIZE==bombY)
            ) {
                entities.get(i).setLives(false);
            }

        }
    }

}
