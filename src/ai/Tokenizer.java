package src.ai;

import java.util.Arrays;

public class Tokenizer {
    public static void main(String[] args) {
        String test = "Hello this is a sentence go and tokenize this. Now with punctuation and a weird_ underscore.";
        Tokenizer tokenizer = new Tokenizer();
        System.out.println(Arrays.toString(tokenizer.tokenize(test)));
    }

    public String[] tokenize(String input) {
        input = input.trim();
        if (input.isEmpty())
            return new String[]{};
        return input.replaceAll("[^a-zA-Z ]", "").replaceAll("\\s+", " ").toLowerCase().trim().split(" ");
    }
}
