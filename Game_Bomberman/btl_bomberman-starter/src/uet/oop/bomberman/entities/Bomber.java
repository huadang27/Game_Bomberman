package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Item.LeverBomb;
import uet.oop.bomberman.entities.Item.Portal;
import uet.oop.bomberman.entities.Item.Speed;
import uet.oop.bomberman.entities.enemy.Doll;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.*;
import static uet.oop.bomberman.BombermanGame.*;


public class Bomber extends Entity {

    private KeyCode keycode;
    private boolean check_nhay = true;
   // private int time_item =0;
    private boolean itemFlame = true;
    private boolean itemStep = true;
    private boolean portal = true;
    public int leverBomb =1;
    private boolean keyleft = false;
    private boolean keyright = false;
    private boolean keyup = false;
    private boolean keydown = false;
    public int count = 0;
    public int khunghinh = 1;
    public String back = "";
    public static List<Bomb> bombList = new ArrayList<>();

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public boolean isPortal() {
        return portal;
    }

    public void setKeycode(KeyCode keycode) {
        this.keycode = keycode;
    }

    public void moving() {


        if (keyup && canMove(x, y - speed)) {

            y -= speed;


        }
        if (keydown && canMove(x, y + speed)) {

            y += speed;

        }
        if (keyleft && canMove(x - speed, y)) {


            x -= speed;

        }
        if (keyright && canMove(x + speed, y)) {

            x += speed;

        }
        easy_move();

    }

    public void keypress() {

        if (keycode == W) {
            keyup = true;
            keyright = false;
            keyleft = false;
            keydown = false;
        }

        if (keycode == S) {
            keyup = false;
            keyright = false;
            keyleft = false;
            keydown = true;
        }

        if (keycode == A) {
            keyup = false;
            keyright = false;
            keyleft = true;
            keydown = false;
        }

        if (keycode == D) {
            keyright = true;
            keyup = false;
            keyleft = false;
            keydown = false;
        }
        if(keycode==SPACE) {
            creat_bomb();
        }

    }


    public void keyReleased(KeyCode keycode) {
        if (keycode == W)
            keyup = false;
        if (keycode == S)
            keydown = false;
        if (keycode == A)
            keyleft = false;
        if (keycode == D)
            keyright = false;

    }

    // kiem tra va cham voi enemy sua lai
    public void check_colide_enemy() {
       int bomerX = (x+24)/Sprite.SCALED_SIZE;
       int bomerY = (y+24)/Sprite.SCALED_SIZE;
        int size = entities.size();
        for (int i = 1; i < size; i++) {
            if (entities.get(i) instanceof Enemy||entities.get(i) instanceof Oneal|| entities.get(i) instanceof Doll) {
                if(bomerX==(entities.get(i).x+24)/Sprite.SCALED_SIZE && bomerY==(entities.get(i).y+24)/Sprite.SCALED_SIZE) {
                    setLives(false);
                }

            }
        }

    }
public void animation_bomber() {
    if (keyright) {

        back = "d";
        if (khunghinh == 1) img = Sprite.player_right.getFxImage();
        else if (khunghinh == 2) img = Sprite.player_right_1.getFxImage();
        else if (khunghinh == 3) img = Sprite.player_right_2.getFxImage();

    }
    if (keyup) {
        back = "w";
        if (khunghinh == 1) img = Sprite.player_up.getFxImage();
        else if (khunghinh == 2) img = Sprite.player_up_1.getFxImage();
        else if (khunghinh == 3) img = Sprite.player_up_2.getFxImage();

    }
    if (keydown) {
        back = "s";
        if (khunghinh == 1) img = Sprite.player_down.getFxImage();
        else if (khunghinh == 2) img = Sprite.player_down_1.getFxImage();
        else if (khunghinh == 3) img = Sprite.player_down_2.getFxImage();

    }
    if (keyleft) {
        back = "a";
        if (khunghinh == 1) img = Sprite.player_left.getFxImage();
        else if (khunghinh == 2) img = Sprite.player_left_1.getFxImage();
        else if (khunghinh == 3) img = Sprite.player_left_2.getFxImage();
    }
    count++;
    if (count > 8) {
        if (khunghinh == 1) khunghinh = 2;
        else if (khunghinh == 2) khunghinh = 3;
        else if (khunghinh == 3) khunghinh = 1;
        count = 0;
    }
    if (!keyright && back.equals("d")) img = Sprite.player_right.getFxImage();
    if (!keyleft && back.equals("a")) img = Sprite.player_left.getFxImage();
    if (!keyup && back.equals("w")) img = Sprite.player_up.getFxImage();
    if (!keydown && back.equals("s")) img = Sprite.player_down.getFxImage();
}
    @Override
    public void update() {

        if (lives) {
            item_nhay();
            eat_item();
            check_Portal();
            check_colide_enemy();
            moving();
            animation_bomber();


        } else {
        animation_die();

        }

    }

    public void item_nhay() {
        if(!(((x+24)/Sprite.SCALED_SIZE== 3 || (x+24)/Sprite.SCALED_SIZE==21)&&(y+24)/Sprite.SCALED_SIZE ==7)) {
            check_nhay=true;
        }



            if ((x + 24) / Sprite.SCALED_SIZE == 3 && (y + 24) / Sprite.SCALED_SIZE == 7&&check_nhay) {
                x = 21 * Sprite.SCALED_SIZE;
                y=7*Sprite.SCALED_SIZE;
                check_nhay = false;
            }
            if ((x + 24) / Sprite.SCALED_SIZE == 21 && (y + 24) / Sprite.SCALED_SIZE == 7&&check_nhay) {
                x = 3 * Sprite.SCALED_SIZE;
                y=7*Sprite.SCALED_SIZE;
                check_nhay = false;
            }
        }
        public void eat_item() {
        int bomX = (x+24)/Sprite.SCALED_SIZE;
        int bomY = (y+24)/Sprite.SCALED_SIZE;
        if(bomX==15 && bomY ==3 && itemStep) {
            speed =3;
            itemStep = false;
            for(int i=entities.size()-1;i>=0;i--) {
                if(entities.get(i) instanceof Speed) {
                    entities.remove(i);
                    return;
                }
            }
        }
            if(bomX==4 && bomY ==5 && itemFlame) {
                itemFlame=false;
                Bomb.lever=2;
                for(int i=entities.size()-1;i>=0;i--) {
                    if(entities.get(i) instanceof LeverBomb) {
                        entities.remove(i);
                        return;
                    }
                }
            }

        }


    public void animation_die() {
        timeDie++;
        if (timeDie == 1) {
            img = Sprite.player_dead1.getFxImage();
        }
        if (timeDie == 10) {
            img = Sprite.player_dead2.getFxImage();
        }
        if (timeDie == 20) {
            img = Sprite.player_dead3.getFxImage();
        }
        if (timeDie == 100) {
            if(this.lives_bomber>1) {
                lives_bomber--;
                lives = true;
                x = Sprite.SCALED_SIZE;
                y = Sprite.SCALED_SIZE;
            }
            else {
                BombermanGame.entities.remove(this);
            }
            timeDie = 0;

        }
    }
    public void creat_bomb() {
        if(bombList.isEmpty()&& lives) {
            Bomb bomb = new Bomb((x+24)/Sprite.SCALED_SIZE,(y+24)/Sprite.SCALED_SIZE,Sprite.bomb.getFxImage());
            entities.add(bomb);
            bombList.add(bomb);
        }

    }
public void easy_move() {
    if ((!canMove(x + speed, y) && keyright) ||(!canMove(x - speed, y) && keyleft)) {
        if (y % Sprite.SCALED_SIZE < 16) {
            y -= speed;
        } else {
            y += speed;
        }
    }

    if ((!canMove(x, y + speed) && keydown)||(!canMove(x, y - speed) && keyup)) {
        if (x % Sprite.SCALED_SIZE < 16) {
            x -= speed;
        } else {
            x += speed;
        }
    }

}
public void check_Portal () {

        if((x+24)/Sprite.SCALED_SIZE==13 && (y+24)/Sprite.SCALED_SIZE==6 &&portal && entities.size()==1 ) {
            portal = false;
            for (int i = stillObjects.size()-1; i <=0 ; i++) {
                if(stillObjects.get(i) instanceof Portal) {
                    stillObjects.remove(i);
                    portal = false;
                    return;
                }
            }
        }
}

}

