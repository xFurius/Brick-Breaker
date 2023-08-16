package com.example.brickbreaker.scenes;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.scene.SubScene;
import com.example.brickbreaker.Glob;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOverScene extends SubScene {
    public GameOverScene(){
        Text score = new Text();
        score.setX(Glob.WINDOW_WIDTH/2 + 70);
        score.setY(Glob.WINDOW_HEIGHT/2 + 145);
        score.setFont(Font.font("calibri", FontWeight.BOLD, FontPosture.REGULAR, 74));
        score.textProperty().bind(FXGL.getWorldProperties().intProperty("Score").asString());
        score.setStroke(Color.WHITE);

        Image gameOver = FXGL.getAssetLoader().loadImage("game-over.png");
        Rectangle r = new Rectangle(600, 600);
        r.setFill(new ImagePattern(gameOver));

        Rectangle restart = new Rectangle(205, 33, Color.TRANSPARENT);
        restart.setX(198);
        restart.setY(243);
        restart.setOnMouseClicked(e -> FXGL.getGameController().startNewGame());

        Rectangle exit = new Rectangle(96, 33, Color.TRANSPARENT);
        exit.setX(252);
        exit.setY(298);
        exit.setOnMouseClicked(e -> FXGL.getGameController().exit());

        Pane pane = new Pane();
        pane.getChildren().addAll(r, restart, exit, score);

        getContentRoot().getChildren().add(pane);
    }
}
