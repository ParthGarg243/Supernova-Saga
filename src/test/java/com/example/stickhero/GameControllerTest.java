package com.example.stickhero;

import com.example.stickhero.GameController;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    @Test
    public void testGetHeroImage() {
        GameController controller = new GameController();
        ImageView heroImage = controller.getHeroImage();
        assertNull(heroImage);
    }

    @Test
    public void testGetFirstPlatform() {
        GameController controller = new GameController();
        Rectangle firstPlatform = controller.getFirstPlatform();
        assertNull(firstPlatform);
    }

    @Test
    public void testGetSecondPlatform() {
        GameController controller = new GameController();
        Rectangle secondPlatform = controller.getSecondPlatform();
        assertNull(secondPlatform);
    }

    @Test
    public void testGetThirdPlatform() {
        GameController controller = new GameController();
        Rectangle thirdPlatform = controller.getThirdPlatform();
        assertNull(thirdPlatform);
    }

    @Test
    public void testGetStick() {
        GameController controller = new GameController();
        Line stick = controller.getStick();
        assertNull(stick);
    }
}