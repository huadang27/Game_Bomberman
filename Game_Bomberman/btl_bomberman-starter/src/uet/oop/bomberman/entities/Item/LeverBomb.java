package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class LeverBomb extends Entity {

    public LeverBomb(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.leverBomb.getFxImage());
    }

    @Override
    public void update() {
        if (!isLives()) {
            BombermanGame.entities.remove(this);
        }

    }
}
