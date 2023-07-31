package com.example.brickbreaker;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.example.brickbreaker.components.HpComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        return FXGL.entityBuilder()
                .type(EntityType.PLAYER)
                .collidable()
                .viewWithBBox(new Rectangle(50, 20, Color.BLACK))
                .at((Glob.WINDOW_HEIGHT / 2) - 20, Glob.WINDOW_WIDTH-100)
                .buildAndAttach();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.BRICK)
                .collidable()
                .viewWithBBox("brick.png")
                .with(new HpComponent(3))
                .buildAndAttach();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.BALL)
                .collidable()
                .viewWithBBox("ball.png")
                .with("velocity", new Point2D(Glob.BALL_VELOCITY_X,  Glob.BALL_VELOCITY_Y))
                .buildAndAttach();
    }
}
