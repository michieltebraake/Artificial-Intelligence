package src.ai;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class Classifier {
    private Tokenizer tokenizer;
    private HashMap<String, Integer> trainedM = new HashMap<>();
    private HashMap<String, Integer> trainedF = new HashMap<>();

    public static void main(String[] args) {
        new Classifier();
    }

    public Classifier() {
        tokenizer = new Tokenizer();
        trainedM.put("!total", 0);
        trainedF.put("!total", 0);
        train("M", trainedM);
        train("F", trainedF);

        System.out.println("Percentage M correct: " + categorizeAll("M"));
        System.out.println("Percentage F correct: " + categorizeAll("F"));
    }

    private void train(String category, HashMap<String, Integer> trained) {
        File folder = new File("blogstrain/" + category);
        for (File file : folder.listFiles()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                for (String line : lines) {
                    for (String word : tokenizer.tokenize(line)) {
                        if (trained.containsKey(word)) {
                            trained.put(word, trained.get(word) + 1);
                        } else {
                            trained.put(word, 1);
                        }
                        trained.put("!total", trained.get("!total") + 1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(trained);
    }

    private double categorizeAll(String category) {
        int correct = 0;
        int total = 0;
        File folder = new File("blogstest/" + category);
        for (File file : folder.listFiles()) {
            if (categorize(file).equals(category)) {
                correct++;
            }
            total++;
        }

        System.out.println("Correct: " + correct + " Total: " + total);

        return (double) correct / total;
    }

    private String categorize(File file) {
        double scoreM = 1;
        double scoreF = 1;
        double k = 1;
        long totalM = trainedM.get("!total");
        long totalF = trainedF.get("!total");
        try {
            List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            for (String line : lines) {
                for (String word : tokenizer.tokenize(line)) {
                    int wordOccurences = 0;
                    if (trainedM.containsKey(word)) {
                        wordOccurences = trainedM.get(word);
                    }
                    scoreM += Math.log((wordOccurences + k) / (totalM + k * totalM));

                    wordOccurences = 0;
                    if (trainedF.containsKey(word)) {
                        wordOccurences = trainedF.get(word);
                    }
                    scoreF += Math.log((wordOccurences + k) / (totalF + k * totalF));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(file.getPath() + ": " + "score m: " + scoreM + ", score f: " + scoreF);
        double result = scoreM - scoreF;
        if (result > 0) {
            return "M";
        } else {
            return "F";
        }
    }
}
