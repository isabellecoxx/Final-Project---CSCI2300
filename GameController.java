import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class GameController {
    private final GameModel model;          
    private final GameView view;            
    private final List<CardButton> buttons;
    private final Timer gameTimer;
    private int timeLeft;
    private int flipDelay = 1000; // default          

    private CardButton firstSelected = null;
    private CardButton secondSelected = null;

    public GameController(GameModel model, GameView view, String difficulty) {
        this.model = model;
        this.view = view;
        this.buttons = view.getCardButtons();

        if (difficulty.equalsIgnoreCase("easy")) {
            timeLeft = 120;
            flipDelay = 1200;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            timeLeft = 90;
            flipDelay = 900;
        } else if (difficulty.equalsIgnoreCase("hard")) {
            timeLeft = 60;
            flipDelay = 700;
        }
        view.updateTimerLabel(timeLeft);

        addCardListeners();
        gameTimer = new Timer(1000, new TimerTick());
        gameTimer.start();
    }
   
    private void addCardListeners() {
        for (CardButton button : buttons) {
            button.addActionListener(e -> handleCardClick(button));
        }
    }

    private void handleCardClick(CardButton button) {
        if (button.isFlipped() || secondSelected != null) return;

        int index = button.getIndex();
        String pet = model.revealPet(index); //model decides what pet to show
        button.flipUp(pet);
        // 🔊 Play flip sound
        SoundManager.playSound("sounds/flip.wav");

        if (firstSelected == null) {
            firstSelected = button;
        } else {
            secondSelected = button;
            processCards();
        }
    }

    private void processCards() {
        int i1 = firstSelected.getIndex();
        int i2 = secondSelected.getIndex();

        //asks model if it's a match
        boolean match = model.checkMatch(i1, i2); //model stores match status

        //delay before hiding mismatched cards
        Timer delay = new Timer(flipDelay, e -> {
            if (!match) {
                firstSelected.flipDown();
                secondSelected.flipDown();
            }else {
                // 🔊 Play match sound
                SoundManager.playSound("sounds/match.wav");
            }

            firstSelected = null;
            secondSelected = null;

            //asks model if game is finished
            if (model.allMatched()) {
                gameTimer.stop();
                SoundManager.playSound("sounds/win.wav"); // 🔊 Win sound
                view.showEndDialog("You matched all pets! You win!");
            }
        });

        delay.setRepeats(false);
        delay.start();
    }

    //handles the countdown timer and ends game if time runs out
    private class TimerTick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            timeLeft--;
            view.updateTimerLabel(timeLeft);

            if (timeLeft <= 0) {
                gameTimer.stop();
                SoundManager.playSound("sounds/lose.wav"); // 🔊 Lose sound
                view.showEndDialog("Time’s up! You lost.");
            }
        }
    }
}

