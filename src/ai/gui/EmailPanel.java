package src.ai.gui;

import javax.swing.*;
import java.util.List;

/**
 * Created by Michiel on 19-12-2014.
 */
public class EmailPanel {
    private JPanel panel1;
    private JTextPane textPane1;

    public void setEmail(List<String> lines) {
        textPane1.setText(lines.toString());
    }
}
