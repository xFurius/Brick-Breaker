package com.example.brickbreaker.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

public class HpComponent extends Component {
    private int hp;

    public HpComponent(int hp){
        this.hp=hp;
    }

    public void changeTexture(Entity brick){
        this.hp -= 1;
        if(this.hp < 1){
            brick.removeFromWorld();
        }else{
            System.out.println(this.hp);
            Image i = switch (this.hp){
                case 2: yield FXGL.getAssetLoader().loadImage("brick_damaged.png");
                case 1: yield FXGL.getAssetLoader().loadImage("brick_destroyed.png");
                default:
                    yield FXGL.getAssetLoader().loadImage("brick.png");
                };
            brick.getViewComponent().removeChild(brick.getViewComponent().getChildren().get(0));
            brick.getViewComponent().addChild(new Texture(i));
        }
    }
}
