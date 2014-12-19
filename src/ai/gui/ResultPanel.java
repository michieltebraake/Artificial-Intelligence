package src.ai.gui;

import javax.swing.*;

/**
 * Created by Michiel on 19-12-2014.
 */
public class ResultPanel {
    private JPanel panel1;
    private JLabel staticLabel;
    private JLabel learnedLabel;

    public void setStaticLabelText(String text) {
        staticLabel.setText(text);
    }

    public void setLearnedLabelText(String text) {
        learnedLabel.setText(text);
    }
}
