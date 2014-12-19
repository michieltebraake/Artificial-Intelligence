package src.ai;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Michiel on 19-12-2014.
 */
public class Classifier {
    private Tokenizer tokenizer;
    private HashMap<String, Integer> trainedHam = new HashMap<>();
    private HashMap<String, Integer> trainedSpam = new HashMap<>();
    private HashMap<String, Integer> staticHam = new HashMap<>();
    private HashMap<String, Integer> staticSpam = new HashMap<>();

    public Classifier() {
        tokenizer = new Tokenizer();
        trainedHam.put("!total", 0);
        trainedSpam.put("!total", 0);
    }

    public HashMap<String, Integer> getTrainedMap(EmailType type) {
        return type == EmailType.HAM ? trainedHam : trainedSpam;
    }

    public void train(EmailType type, List<String> lines) {
        HashMap<String, Integer> trained = getTrainedMap(type);
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
    }

    public void finishStaticTraining() {
        staticHam = (HashMap<String, Integer>) trainedHam.clone();
        staticSpam = (HashMap<String, Integer>) trainedSpam.clone();
    }

    public EmailType categorize(List<String> lines, boolean learning) {
        HashMap<String, Integer> ham;
        HashMap<String, Integer> spam;
        if (learning) {
            ham = trainedHam;
            spam = trainedSpam;
        } else {
            ham = staticHam;
            spam = staticSpam;
        }

        double scoreHam = 1;
        double scoreSpam = 1;
        double k = 1;
        long totalM = ham.get("!total");
        long totalF = spam.get("!total");

        for (String line : lines) {
            for (String word : tokenizer.tokenize(line)) {
                int wordOccurences = 0;
                //Check if word is in vocabulary at all
                if (ham.containsKey(word) || spam.containsKey(word)) {
                    if (ham.containsKey(word)) {
                        wordOccurences = ham.get(word);
                    }
                    scoreHam += Math.log((wordOccurences + k) / (totalM + k * totalM));

                    wordOccurences = 0;
                    if (spam.containsKey(word)) {
                        wordOccurences = spam.get(word);
                    }
                    scoreSpam += Math.log((wordOccurences + k) / (totalF + k * totalF));
                }
            }
        }

        //System.out.println(file.getPath() + ": " + "score m: " + scoreM + ", score f: " + scoreF);
        double result = scoreHam - scoreSpam;
        if (result > 0) {
            return EmailType.HAM;
        } else {
            return EmailType.SPAM;
        }
    }
}
