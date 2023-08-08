package com.example.brickbreaker;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.example.brickbreaker.components.HpComponent;
import com.example.brickbreaker.menu.CustomSceneFactory;
import com.example.brickbreaker.scenes.GameOverScene;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.Map;
import java.util.Random;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class BrickBreakerApp extends GameApplication {
    private Entity player;
    private Entity ball;
    private int[] startingX;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Brick Breaker");
        gameSettings.setHeight(Glob.WINDOW_HEIGHT);
        gameSettings.setWidth(Glob.WINDOW_WIDTH);
        gameSettings.setSceneFactory(new CustomSceneFactory());
        gameSettings.setMainMenuEnabled(true);
    }

    @Override
    protected void onPreInit() {
        startingX = new int[5];
        int x = 20;
        for(int i=0; i<5; i++){
            startingX[i] = x;
            x += 35;
        }
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player");

        spawnBricks();

        ball = spawn("ball");
    }

    private void spawnBricks(){
        Random r = new Random();
        for(int y = 30 ;y < 300; y+= 30){
            int x = startingX[r.nextInt(0, startingX.length)];
            for(int i=x; i < Glob.WINDOW_WIDTH - x; i += 70){
                spawn("brick", i, y);
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

        Point2D ballPosition = ball.getPosition();
        if(ballPosition.getX() < 0 || ballPosition.getX() > Glob.WINDOW_WIDTH - 15){
            ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
        }else if(ballPosition.getY() < 0){
            ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
        }else if(ballPosition.getY() > Glob.WINDOW_HEIGHT - 15){
            FXGL.getSceneService().pushSubScene(new GameOverScene());
        }
    }

    @Override
    protected void initPhysics() {
        onCollision(EntityType.BALL, EntityType.PLAYER, (ball, player) -> {
            Point2D ballVelocity = ball.getObject("velocity");
            ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
            return null;
        });

        new Thread(() ->{
            onCollision(EntityType.BALL, EntityType.BRICK, (ball, brick) -> {
                Point2D ballVelocity = ball.getObject("velocity");
                brick.getComponent(HpComponent.class).changeTexture(brick);

                if(ball.getRightX() == brick.getX() && (ball.getY() < brick.getBottomY() && ball.getBottomY() > brick.getY())){ //left
                    ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
                }else if(ball.getX() == brick.getRightX() && (ball.getY() < brick.getBottomY() && ball.getBottomY() > brick.getY())){ //right
                    ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
                }else{
                    ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
                }

//                brick.removeFromWorld();
                return null;
            });
        }).run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}