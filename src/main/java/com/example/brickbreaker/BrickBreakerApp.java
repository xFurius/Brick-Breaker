package com.example.brickbreaker;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class BrickBreakerApp extends GameApplication {
    private Entity player;
    private Entity ball;
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setTitle("Brick Breaker");
        gameSettings.setHeight(Glob.WINDOW_HEIGHT);
        gameSettings.setWidth(Glob.WINDOW_WIDTH);
        gameSettings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player");
        spawn("brick", 100, 100);
        spawn("brick", 200, 100);
        spawn("brick", 300, 100);
        spawn("brick", 400, 100);
        spawn("brick", 100, 200);
        spawn("brick", 150, 200);
        ball = spawn("ball", 500, 300);

        FXGL.entityBuilder().type(EntityType.BORDER).collidable().viewWithBBox(new Rectangle(570,570, Color.TRANSPARENT)).at(15,15).buildAndAttach();
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

        onCollision(EntityType.BALL, EntityType.BRICK, (ball, brick) -> {
            Point2D ballVelocity = ball.getObject("velocity");
            Point2D ballPosition = ball.getPosition();

            System.out.println("BRICK POS: " + brick.getCenter());
            System.out.println("BALL POS: " + ballPosition);

            if(ballPosition.getY() == brick.getCenter().getY() - 35 || ballPosition.getY() == brick.getCenter().getY() + 15){ //check if ball collided with top or bottom of the brick
                ball.setProperty("velocity", new Point2D(ballVelocity.getX(), -ballVelocity.getY()));
            }else{ //left or right of the brick
                ball.setProperty("velocity", new Point2D(-ballVelocity.getX(), ballVelocity.getY()));
            }
            brick.removeFromWorld();
            return null;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}