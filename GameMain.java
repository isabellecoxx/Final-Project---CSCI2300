public class GameMain {

    public static void main(String[] args){
        
        // creates instance of model class with 12 cards (for game functionality)
        GameModel model = new GameModel(12);
        // creates instance of view class (GUI)
        GameView view = new GameView(model);
        // creates instance of controller class, connects model and view
        GameController controller = new GameController(model, view);
        
    }
}