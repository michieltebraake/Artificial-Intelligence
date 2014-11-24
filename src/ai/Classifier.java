package src.ai;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
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
    }

    private void train(String category, HashMap<String, Integer> trained) {
        File folder = new File("blogstrain/" + category);
        for (File file : folder.listFiles()) {
            try {
                List<String> lines = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                for (String line : lines) {
                    for (String word : tokenizer.tokenize(line)) {
                        if (word.equals("")) {
                            System.out.println(Arrays.toString(tokenizer.tokenize(line)));
                        }
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

    private int categorize() {
        return 0;
    }

    private int getProbabilty() {

        return 0;
    }
}
