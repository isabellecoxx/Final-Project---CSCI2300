import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameMain {

    // defines file used for saving and loading the game state
    private static String SAVE_FILE = "gamestate.ser";
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
        GameModel model;

        // asks user if they'd like to load a previous game, saves response to choice variable
        int choice = JOptionPane.showConfirmDialog(null,
        "do you want to load the previous game?",
        "load game", JOptionPane.YES_NO_OPTION);

        // if user chooses "yes", load previous game state file
        if (choice == JOptionPane.YES_OPTION){
            model = GameModel.loadGame(SAVE_FILE);
            // if there is no saved game or error loading it, creates new game model
            if(model == null){
                model = new GameModel(difficulty);
            }
        }
        // if user chooses "no", create new game model
        else{
            model = new GameModel(difficulty); 
        }


        // creates instance of view class (GUI)
        GameView view = new GameView(model);
        // creates instance of controller class, connects model and view
        GameController controller = new GameController(model, view, difficulty);

        GameModel final_model = model; 
        
        // add window listener to save game when window is closed
        JFrame frame = view.getFrame();
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                // saves the game when window is closing
                final_model.saveGame(SAVE_FILE);
            }
        });
    }
   
}

