public class GameApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameModel model = new GameModel(4); // 4x4 = 16 cards
            GameView view = new GameView(model);
            new GameController(model, view); // Person 3's job
        });
    }
}
