package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Item.LeverBomb;
import uet.oop.bomberman.entities.Item.Step;
import uet.oop.bomberman.entities.enemy.Enemy;
import uet.oop.bomberman.entities.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.menu.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BombermanGame extends Application {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 13;
    public static boolean[][] canmove = new boolean[50][50];
    public static GraphicsContext gc;
    public Canvas canvas;
    public static int count = 0;
    public static int point = 0;
    public static int time = 200 * 60;
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public static Group root = new Group();

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas


        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH - 5 * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE * (HEIGHT + 1));
        gc = canvas.getGraphicsContext2D();

        // Tao root container
//
        Text _point = new Text();
        Text _time = new Text();
        Text _lives = new Text();
        Text gameover = new Text(350, 300,"");
        setPoint(root, _time, _point, _lives);
        root.getChildren().add(canvas);


        // Tao scene
        Scene scene = new Scene(root);
        scene.setCursor(new ImageCursor(new Image("/textures/cursor.png")));
        stage.setTitle("Bomberman");
        stage.getIcons().add(new Image("/sprites/icon.png"));

        // Them scene vao stage
        stage.setScene(scene);

        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                time--;
                updateSetpoint(_point, _time, _lives);
                render();
                update();

                if (check_endgame() || check_wingame()) {
                    count++;
                    if (check_endgame()) {
                        gameover.setText("Game Over!");
                    } else {
                        gameover.setText("You Win!");
                    }

                        gameover.setStyle("-fx-font: 50 Impact;");
                      if(count==2) root.getChildren().add(gameover);
                    if (count > 100) {

                        Menu.newgame(stage, this);
                        root.getChildren().remove(gameover);
                        reset_game();

                        count = 0;
                    }
                }

            }
        };


        pausegame(timer, stage);
        Menu menu = new Menu(stage, root, timer);


        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        reset_game();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {

                ((Bomber) entities.get(0)).setKeycode(keyEvent.getCode());
                ((Bomber) entities.get(0)).keypress();
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                ((Bomber) entities.get(0)).keyReleased(keyEvent.getCode());
            }
        });
    }


    public void createMap() throws FileNotFoundException {


        File myObj = new File("src\\uet\\oop\\bomberman\\map.txt");
        Scanner myReader = new Scanner(myObj);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 25; j++) {
                Entity object;
                int data = myReader.nextInt();
                if (data == 0) {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    stillObjects.add(object);
                }
                if (data == 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                    canmove[j][i] = true;
                }
                if (data == 2) {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                    canmove[j][i] = true;
                }

            }
        }
        myReader.close();

    }

    public void creatEntinys() {

        Entity enemy = new Enemy(10, 1, Sprite.balloom_left1.getFxImage());
        Entity enemy2 = new Enemy(21, 11, Sprite.balloom_left1.getFxImage());
        Entity enemy3 = new Enemy(3, 11, Sprite.balloom_left1.getFxImage());
        Entity oneal1 = new Oneal(19, 7, Sprite.oneal_left1.getFxImage());
        Entity oneal2 = new Oneal(11, 11, Sprite.oneal_left1.getFxImage());


        entities.add(enemy);
        entities.add(enemy2);
        entities.add(enemy3);
        entities.add(oneal1);
        entities.add(oneal2);
    }

    public void creatItem() {
        Step start = new Step(3, 7);
        Step end = new Step(21, 7);

    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(gc);
        }

    }

    public boolean check_endgame() {
        if (!entities.isEmpty()) {
            if (!(entities.get(0) instanceof Bomber) || time <= 0) return true;
            return false;
        }
        return true;

    }

    public boolean check_wingame() {
        Bomber bomber = (Bomber) entities.get(0);
        if (!bomber.isPortal()) {
            return true;
        }
        return false;
    }

    public void reset_game() {


        entities.clear();
        stillObjects.clear();
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        bomberman.leverBomb = 1;
        entities.add(bomberman);
        bomberman.setSpeed(2);

        creatEntinys();

        try {
            createMap();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        creatItem();
        time = 200 * 60;
        point = 0;


    }


    public void pausegame(AnimationTimer timer, Stage stage) {
        ImageView pause_img = new ImageView(new Image("/textures/pause.png/"));
        Button pause = new Button("", pause_img);
        Button start = new Button("NEW GAME");
        Button _continue = new Button("CONTINUE");
        Button exit = new Button("EXIT");
        pause.setLayoutX(900);
        pause.setLayoutY(2);
        pause.setBackground(Background.EMPTY);

        pause.setFocusTraversable(false);
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {

                timer.stop();
                root.getChildren().addAll(_continue, exit, start);
                root.getChildren().remove(pause);
            }
        });

        root.getChildren().add(pause);

        start.setLayoutX(440);
        start.setLayoutY(350);
        start.setScaleX(5);
        start.setScaleY(2);
        start.setTextFill(Color.SNOW);
        start.setStyle("-fx-background-color: " + Color.YELLOWGREEN.toString().replace("0x", "#"));
        start.setFont(Font.font("Impact", FontWeight.BOLD, 12));

        animationButon(start);
        start.setOnAction(event -> {
            reset_game();
            root.getChildren().removeAll(start, exit, _continue);
            root.getChildren().add(pause);
            timer.start();

        });

        _continue.setLayoutX(440);
        _continue.setLayoutY(280);
        _continue.setScaleX(5);
        _continue.setScaleY(2);
        _continue.setTextFill(Color.SNOW);
        _continue.setStyle("-fx-background-color: " + Color.YELLOWGREEN.toString().replace("0x", "#"));
        _continue.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        animationButon(_continue);
        _continue.setOnAction(event -> {
            root.getChildren().removeAll(start, exit, _continue);
            root.getChildren().add(pause);
            timer.start();
        });
        exit.setLayoutX(440);
        exit.setLayoutY(420);
        exit.setScaleX(5);
        exit.setScaleY(2);
        exit.setTextFill(Color.SNOW);
        exit.setStyle("-fx-background-color: " + Color.YELLOWGREEN.toString().replace("0x", "#"));
        exit.setFont(Font.font("Impact", FontWeight.BOLD, 12));
        animationButon(exit);
        exit.setOnAction(event -> {
            root.getChildren().removeAll(start, exit, _continue);
            stage.close();
        });

    }

    public void animationButon(Button button) {
        button.onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setTextFill(Color.SKYBLUE);

            }
        });
        button.onMouseExitedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                button.setTextFill(Color.SNOW);
            }
        });
    }


    public void setPoint(Group root, Text timer, Text point, Text lives) {
        Rectangle rectangle = new Rectangle(0, 0, 20 * Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        rectangle.setFill(Color.LIGHTGREEN);
        timer.setX(200);
        timer.setY(30);
        point.setX(Sprite.SCALED_SIZE);
        point.setY(30);

        lives.setX(350);
        lives.setY(30);
        lives.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        point.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        timer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        root.getChildren().addAll(rectangle, point, timer, lives);


    }

    public void updateSetpoint(Text _point, Text timer, Text live) {
        _point.setText("Point :" + point);
        timer.setText("Time :" + time / 60);
        live.setText("Lives :" + entities.get(0).lives_bomber);
    }
}
