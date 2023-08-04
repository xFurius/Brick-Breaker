package com.example.brickbreaker;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.brickbreaker.menu.CustomSceneFactory;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class BrickBreakerApp extends GameApplication {
    private Entity player;
    private Entity ball;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Brick Breaker");
        gameSettings.setHeight(Glob.WINDOW_HEIGHT);
        gameSettings.setWidth(Glob.WINDOW_WIDTH);
        gameSettings.setSceneFactory(new CustomSceneFactory());
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());

        FXGL.entityBuilder().type(EntityType.BORDER).collidable().viewWithBBox(new Rectangle(570,570, Color.TRANSPARENT)).at(15,15).buildAndAttach();

        player = spawn("player");

        spawnBricks();

//        spawn("brick", 310, 300);
        ball = spawn("ball", 280, 350);
    }

    private void spawnBricks(){
        Random r = new Random();
        for(int y = 100; y < 300; y += 25){
            int temp = r.nextInt(10, 100);
            for(int x = temp; x<Glob.WINDOW_WIDTH - temp; x += 65){
                spawn("brick", x, y);
            }
        }
    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () ->{
            if(player.getPosition().getX() < 550){
                player.translateX(3);
            }
        });

        FXGL.onKey(KeyCode.A, () ->{
            if(player.getPosition().getX() > 0){
                player.translateX(-3);
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
//        vars.put("Score", 0);
    }

    @Override
    protected void onUpdate(double tpf) {
        Point2D ballVelocity = ball.getObject("velocity");
        ball.translate(ballVelocity);
    }

    @Override
    protected void initPhysics() {
        onCollision(EntityType.BALL, EntityType.PLAYER, (ball, player) -> {
            Point2D ballVelocity = ball.getObject("velocity");
            ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
            return null;
        });

        onCollisionEnd(EntityType.BALL, EntityType.BORDER, (ball, border) -> {
            Point2D ballVelocity = ball.getObject("velocity");
            Point2D ballPosition = ball.getPosition();
            if(ballPosition.getY() > 580){ //bottom rect wall
                ball.removeFromWorld();
            }else if(ballPosition.getX() < 0 || ballPosition.getX() > 580){ //left and right rect wall
                ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
            }else{ //top rect wall
                ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
            }
            return null;
        });

        new Thread(() ->{
            onCollisionBegin(EntityType.BALL, EntityType.BRICK, (ball, brick) -> {
                Point2D ballVelocity = ball.getObject("velocity");
                //            brick.getComponent(HpComponent.class).changeTexture(brick);

                if(ball.getRightX() == brick.getX() && (ball.getY() < brick.getBottomY() && ball.getBottomY() > brick.getY())){ //left
                    ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
                }else if(ball.getX() == brick.getRightX() && (ball.getY() < brick.getBottomY() && ball.getBottomY() > brick.getY())){ //right
                    ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
                }else{
                    ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
                }

                brick.removeFromWorld();
                return null;
            });
        }).run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}