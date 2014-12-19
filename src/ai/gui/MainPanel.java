package src.ai.gui;

import src.ai.EmailType;
import src.ai.InteractiveLearner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Michiel on 19-12-2014.
 */
public class MainPanel {
    private JPanel mainPanel;
    private JButton hamButton;
    private JButton spamButton;
    private ResultPanel resultPanel;
    private EmailPanel emailPanel;

    public MainPanel(final InteractiveLearner interactiveLearner) {
        hamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interactiveLearner.addEmail(EmailType.HAM);
            }
        });
        spamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interactiveLearner.addEmail(EmailType.SPAM);
            }
        });
    }

    public EmailPanel getEmailPanel() {
        return emailPanel;
    }

    public ResultPanel getResultPanel() {
        return resultPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
