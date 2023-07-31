package com.example.brickbreaker.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.app.scene.SceneFactory;

public class CustomSceneFactory extends SceneFactory {
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenu(MenuType.MAIN_MENU);
    }
}
