package src.ai;

import src.ai.gui.MainPanel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Michiel on 19-12-2014.
 */
public class InteractiveLearner {
    private JFrame frame;
    private Classifier classifier;
    private MainPanel mainPanel;
    private List<List<String>> allLines = new ArrayList<>();
    private int currentEmail = 0;

    public InteractiveLearner() {
        classifier = new Classifier();

        //Train with known data
        train(EmailType.HAM);
        train(EmailType.SPAM);

        //Load test data
        loadTest();

        frame = new JFrame("Interactive Learner");

        mainPanel = new MainPanel(this);

        //Make frame full screen
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(mainPanel.getMainPanel());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        loadEmail();
    }

    public static void main(String[] args) {
        new InteractiveLearner();
    }

    public void loadEmail() {
        List<String> lines = allLines.get(currentEmail);
        mainPanel.getEmailPanel().setEmail(lines);
        mainPanel.getResultPanel().setLearnedLabelText(classifier.categorize(lines, true).toString());
        mainPanel.getResultPanel().setStaticLabelText(classifier.categorize(lines, false).toString());
    }

    public void addEmail(EmailType type) {
        classifier.train(type, allLines.get(currentEmail));
        currentEmail++;
        loadEmail();
    }

    private void loadTest() {
        System.out.println("Loading test data...");
        File folder = new File("spammail/test");
        for (File file : folder.listFiles()) {
            try {
                List<String> lines = new ArrayList<>();
                lines.add(file.getName() + ":");
                lines.addAll(Files.readAllLines(file.toPath(), Charset.defaultCharset()));
                allLines.add(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Done loading test data. Scrambling list.");
        long seed = System.nanoTime();
        Collections.shuffle(allLines, new Random(seed));
        System.out.println("Done scrambling list");
    }

    private void train(EmailType type) {
        System.out.println("Training data...");
        File folder = new File("spammail/" + type.toString().toLowerCase());
        for (File file : folder.listFiles()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                classifier.train(type, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        classifier.finishStaticTraining();
        System.out.println("Done training.");
    }
}
