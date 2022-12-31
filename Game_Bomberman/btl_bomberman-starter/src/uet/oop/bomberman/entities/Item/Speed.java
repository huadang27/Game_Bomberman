package uet.oop.bomberman.entities.Item;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Speed extends Entity {

    public Speed(int xUnit, int yUnit) {

        super(xUnit, yUnit,Sprite.item_speed.getFxImage());
        BombermanGame.entities.add(this);
    }

    @Override
    public void update() {
        if(!isLives()) {
            BombermanGame.entities.remove(this);
        }

    }
}
