package com.example.brickbreaker.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.FontType;
import com.example.brickbreaker.Glob;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class MainMenu extends FXGLMenu {
    public MainMenu(MenuType type) {
        super(MenuType.MAIN_MENU);

        Image wall = FXGL.getAssetLoader().loadImage("brick-wall.png");
        Rectangle r = new Rectangle(600, 600);
        r.setFill(new ImagePattern(wall));

        Rectangle startGame = new Rectangle(150, 40, Color.TRANSPARENT);
        startGame.setX((Glob.WINDOW_WIDTH - 150) / 2);
        startGame.setY((Glob.WINDOW_HEIGHT - 120) / 2);
        startGame.setOnMouseClicked(e -> fireNewGame());

        Rectangle exitGame = new Rectangle(110, 40, Color.TRANSPARENT);
        exitGame.setX((Glob.WINDOW_WIDTH - 110) / 2);
        exitGame.setY((Glob.WINDOW_HEIGHT - 20) / 2);
        exitGame.setOnMouseClicked(e -> getController().exit());



        getContentRoot().getChildren().addAll(r, startGame, exitGame);
    }
}
