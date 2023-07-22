package com.example.brickbreaker;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GameEntityFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData data){
        return FXGL.entityBuilder()
                .at((Glob.WINDOW_HEIGHT / 2) - 20, Glob.WINDOW_WIDTH-100)
                .viewWithBBox(new Rectangle(50, 20, Color.BLACK))
                .with(new CollidableComponent(true))
                .type(EntityType.PLAYER)
                .buildAndAttach();
    }

    @Spawns("brick")
    public Entity newBrick(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.BRICK)
                .with(new CollidableComponent(true))
                .viewWithBBox(new Rectangle(30, 20, Color.BROWN))
                .buildAndAttach();
    }

    @Spawns("ball")
    public Entity newBall(SpawnData data){
        return FXGL.entityBuilder(data)
                .type(EntityType.BALL)
                .with(new CollidableComponent(true))
                .viewWithBBox(new Circle(10, 10, 10))
                .with("velocity", new Point2D(Glob.BALL_VELOCITY,  Glob.BALL_VELOCITY))
                .buildAndAttach();
    }
}
