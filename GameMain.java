import javax.swing.JOptionPane;

public class GameMain {
    public static void main(String[] args) {
        // Ask the user for difficulty
        String[] options = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(
                null,
                "Choose your difficulty:",
                "Pet Match Difficulty",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]  // default: "Medium"
        );
   
        // If they cancel or close the window, default to Medium
        if (difficulty == null) {
            difficulty = "Medium";
        }
   
        // Initialize the game with the chosen difficulty
        GameModel model = new GameModel(difficulty);
        GameView view = new GameView(model);
        GameController controller = new GameController(model, view, difficulty);
    }
   
}

