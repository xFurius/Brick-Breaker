package com.example.brickbreaker;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

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
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory());

        player = spawn("player");
        spawn("brick", 100, 100);
        ball = spawn("ball", 200, 200);
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
        vars.put("Score", 0);
    }

    @Override
    protected void onUpdate(double tpf) {
        ball.translate((Point2D) ball.getObject("velocity"));
    }

    @Override
    protected void initPhysics() {
        onCollision(EntityType.BALL, EntityType.PLAYER, (ball, player) -> {
            ball.removeFromWorld();
            return null;
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}