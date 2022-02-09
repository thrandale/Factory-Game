package helpers;

import java.awt.Color;

public class Constants {

    public class GameConstants {
        public static final int GAME_WIDTH = 800;
        public static final int GAME_HEIGHT = 640;
        public static final int MAP_WIDTH = GameConstants.GAME_WIDTH * 4;
        public static final int MAP_HEIGHT = GameConstants.GAME_HEIGHT * 4;
        public static final int GAME_FPS = 60;
        public static final float GAME_SCALE_SPEED = 0.03125f; // (1 / 32)
        public static final int STARTING_GOLD = 2000;
        public static final int MAX_GOLD = 999999;
        public static final String FONT = "Arial";
        public static final float MAX_SCALE = 2;
        public static final float MIN_SCALE = ((GAME_WIDTH / 32.00f) / (MAP_WIDTH / 32.00f));
        public static final int DEFAULT_ANIMATION_SPEED = 8;
    }

    public class Colors {
        public static final Color BUTTON_COLOR = new Color(33, 110, 158);
        public static final Color BUTTON_HOVERED_COLOR = new Color(24, 80, 115);
        public static final Color BACKGROUND_COLOR = new Color(184, 110, 7);
        public static final Color VALID_COLOR = new Color(40, 30, 176, 100);
        public static final Color INVALID_COLOR = new Color(235, 64, 52, 100);

    }

    public class MapTiles {
        public static final int GRASS = 0;
        public static final int FOREST = 1;
        public static final int IRON = 2;
        public static final int COPPER = 3;
    }

    public class BeltTypes {
        public static final int BELT_STRAIGHT = 0;
        public static final int BELT_CORNER_C = 1;
        public static final int BELT_CORNER_CC = 2;
        public static final int BELT_2IN_ADJ_C = 3;
        public static final int BELT_2IN_ADJ_CC = 4;
        public static final int BELT_2IN_OPP = 5;
        public static final int BELT_3IN = 6;
    }

    public class SpriteSheet {
        public static final int GRASS_POS[] = { 4, 0 };
        public static final int FOREST_POS[] = { 4, 1 };
        public static final int IRON_POS[] = { 4, 2 };
        public static final int COPPER_POS[] = { 4, 3 };

        public static final int COIN_POS[] = { 4, 4 };
        public static final int BELT_STRAIGHT_POS = 0;
        public static final int BELT_CORNER_POS = 1;
        public static final int BELT_2IN_ADJ_POS = 2;
        public static final int BELT_2IN_OPP_POS = 3;
        public static final int BELT_3IN_POS = 4;
    }
}
