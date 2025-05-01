import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView {
    private final JFrame frame;
    private final JPanel boardPanel;
    private final JLabel timerLabel;
    private final List<CardButton> buttons;

    public GameView(GameModel model) {
        frame = new JFrame("Pet Match Game - Alpha");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Timer label
        timerLabel = new JLabel("Time: 120", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(timerLabel, BorderLayout.NORTH);

        // Card grid panel
        int gridSize = (int) Math.sqrt(model.getTotalCards());
        boardPanel = new JPanel(new GridLayout(gridSize, gridSize));
        buttons = new ArrayList<>();

        // Add card buttons to grid
        for (int i = 0; i < model.getTotalCards(); i++) {
            CardButton btn = new CardButton(i);
            buttons.add(btn);
            boardPanel.add(btn);
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setSize(400, 450);
        frame.setVisible(true);
    }

    public List<CardButton> getCardButtons() {
        return buttons;
    }

    public void updateTimerLabel(int secondsLeft) {
        timerLabel.setText("Time: " + secondsLeft);
    }

    public void showEndDialog(String message) {
        int option = JOptionPane.showOptionDialog(frame, message, "Game Over",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[]{"Play Again", "Quit"}, "Play Again");

        if (option == 0) {
            // Restart
            SwingUtilities.invokeLater(() -> frame.dispose());
            GameMain.main(null); // Reload the game
        } else {
            System.exit(0);
        }
    }

    public JFrame getFrame(){
        return frame;
    }
}
