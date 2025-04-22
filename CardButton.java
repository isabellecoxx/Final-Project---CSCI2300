import javax.swing.*;

public class CardButton extends JButton {
    private final int index;
    private boolean isFlipped = false;

    public CardButton(int index) {
        this.index = index;
        setText("❓");
        setFont(getFont().deriveFont(30f));
    }

    public int getIndex() {
        return index;
    }

    public void flipUp(String pet) {
        setText(pet);
        isFlipped = true;
    }

    public void flipDown() {
        setText("❓");
        isFlipped = false;
    }

    public boolean isFlipped() {
        return isFlipped;
    }
}
