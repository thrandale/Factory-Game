package main;

public class Launcher {
    private static Game game;
    public static void main(String[] args) {

        // create the game and start it
        game = new Game();
        game.start();
    }
}