package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Step extends Entity {
    public Step(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.item_nhay.getFxImage());
        BombermanGame.stillObjects.add(this);
    }

    @Override
    public void update() {
    }
}
